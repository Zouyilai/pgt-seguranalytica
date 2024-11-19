package seguranalytica.voucher;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VoucherLogRepository extends CrudRepository<VoucherLogModel, String> {

    public List<VoucherLogModel> findByIdVoucher(String idVoucher);

    public Long countByIdVoucher(String idVoucher);
    
}
