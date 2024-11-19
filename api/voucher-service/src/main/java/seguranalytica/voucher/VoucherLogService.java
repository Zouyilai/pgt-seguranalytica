package seguranalytica.voucher;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherLogService {

    @Autowired
    private VoucherLogRepository voucherLogRepository;

    public VoucherLog create(VoucherLog voucherLog) {
        voucherLog.used(new Date());
        return voucherLogRepository.save(new VoucherLogModel(voucherLog)).to();
    }

    public VoucherLog findById(String id) {
        return voucherLogRepository.findById(id)
            .map(VoucherLogModel::to)
            .orElse(null);
    }

    public List<VoucherLog> findByIdVoucher(String id) {
        return voucherLogRepository.findByIdVoucher(id).stream()
            .map(VoucherLogModel::to)
            .toList();
    }

    public Long countByIdVoucher(String id) {
        return voucherLogRepository.countByIdVoucher(id);
    }
    
}
