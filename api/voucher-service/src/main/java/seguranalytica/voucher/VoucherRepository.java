package seguranalytica.voucher;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VoucherRepository extends CrudRepository<VoucherModel, String> {

    public List<VoucherModel> findByIdAccount(String idAccount);
    
}
