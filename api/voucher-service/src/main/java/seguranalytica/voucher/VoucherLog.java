package seguranalytica.voucher;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder
@Data @Accessors(fluent = true)
public class VoucherLog {

    private String id;
    private Voucher voucher;
    private String origin;
    private Date used;
    
}
