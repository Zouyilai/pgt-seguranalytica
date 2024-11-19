package seguranalytica.voucher;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherLogService voucherLogService;

    public Voucher create(Voucher voucher) {
        voucher
            .creation(new Date())
            .notBefore(voucher.notBefore() == null ? new Date() : voucher.notBefore());
        if (voucher.account() == null) {
            throw new VoucherException(HttpStatus.BAD_REQUEST, "account is empty");
        }
        return voucherRepository.save(new VoucherModel(voucher)).to();
    }

    public Voucher findById(String id) {
        return voucherRepository.findById(id)
            .map(m -> 
                m.to()
                    .numberOfDownloads(voucherLogService.countByIdVoucher(id))
                    .useds(voucherLogService.findByIdVoucher(id))
            )
            .orElse(null);
    }

    public List<Voucher> findByIdAccount(String id) {
        return voucherRepository.findByIdAccount(id).stream()
            .map(m -> m.to().numberOfDownloads(voucherLogService.countByIdVoucher(id)))
            .toList();
    }

    public void revoke(String id, String idAccount) {
        Voucher voucher = voucherRepository.findById(id)
            .map(VoucherModel::to)
            .orElseThrow(() -> new VoucherException(HttpStatus.NOT_FOUND, "voucher " + id + " not found"));

        if (!voucher.account().id().equals(idAccount)) {
            throw new VoucherException(HttpStatus.UNAUTHORIZED, "voucher " + id + " is unauthorized");
        }

        if (voucher.revoked() != null) {
            throw new VoucherException(HttpStatus.NOT_FOUND, "voucher " + id + " had been revoked at " + voucher.revoked());
        }

        voucher.revoked(new Date());
        voucherRepository.save(new VoucherModel(voucher));
    }

    public void consume(String idAccount, String uuid, String origin) {
        Voucher voucher = findById(uuid);
        if (voucher == null) throw new VoucherException(HttpStatus.NOT_FOUND, "voucher not found");
        if (idAccount != null && !voucher.account().id().equals(idAccount)) throw new VoucherException(HttpStatus.UNAUTHORIZED, "voucher unathorized");
        if (voucher.revoked() != null) throw new VoucherException(HttpStatus.UNAUTHORIZED, "revoked at " + voucher.revoked());
        final Date now = new Date();
        if (voucher.notBefore() != null && now.compareTo(voucher.notBefore()) < 0) throw new VoucherException(HttpStatus.UNAUTHORIZED, "not before " + voucher.notBefore());
        if (voucher.notAfter() != null && now.compareTo(voucher.notAfter()) > 0) throw new VoucherException(HttpStatus.UNAUTHORIZED, "not after " + voucher.notAfter());
        voucherLogService.create(VoucherLog.builder().voucher(voucher).origin(origin).build());
    }
    
}
