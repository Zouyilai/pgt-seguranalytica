package seguranalytica.voucher;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import seguranalytica.account.AccountOut;

@Builder
@Data @Accessors(fluent = true)
public class Voucher {

    private String id;
    private String alias;
    private Date notBefore;
    private Date notAfter;
    private Date revoked;
    private Long numberOfDownloads;
    private AccountOut account;
    private List<VoucherLog> useds;
    private Date creation;
    
}
