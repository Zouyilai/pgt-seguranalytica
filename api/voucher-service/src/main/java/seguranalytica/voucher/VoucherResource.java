package seguranalytica.voucher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import seguranalytica.account.AccountOut;

@RestController
public class VoucherResource implements VoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private HttpServletRequest context;

    @Override
    public ResponseEntity<VoucherOut> create(String idAccount, VoucherIn in) {
        Voucher voucher = VoucherParser.to(in)
            .account(AccountOut.builder().id(idAccount).build());
        voucher = voucherService.create(voucher);
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(voucher.id())
                .toUri())
            .body(VoucherParser.to(voucher));
    }

    @Override
    public ResponseEntity<Void> revoke(String idAccount, String idVoucher) {
        voucherService.revoke(idVoucher, idAccount);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<VoucherOut> detail(String idAccount, String idVoucher) {
        Voucher voucher = voucherService.findById(idVoucher);
        if (voucher == null) throw new VoucherException(HttpStatus.NOT_FOUND, "voucher not found");
        if (!voucher.account().id().equals(idAccount)) throw new VoucherException(HttpStatus.UNAUTHORIZED, "voucher unathorized");
        return ResponseEntity.ok().body(VoucherParser.to(voucher));
    }

    @Override
    public ResponseEntity<List<VoucherOut>> listByAccount(String idAccount) {
        return ResponseEntity.ok().body(
            voucherService.findByIdAccount(idAccount).stream().map(VoucherParser::to).toList()
        );
    }

    @Override
    public ResponseEntity<Void> consume(String idAccount, String idVoucher) {
        String origin = context.getRemoteAddr();
        voucherService.consume(idAccount, idVoucher, origin);
        return ResponseEntity.ok().build();
    }
    
}
