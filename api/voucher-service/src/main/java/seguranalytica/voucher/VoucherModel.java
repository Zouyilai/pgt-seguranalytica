package seguranalytica.voucher;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import seguranalytica.account.AccountOut;

@Entity
@Table(name = "voucher")
@AllArgsConstructor @NoArgsConstructor
public class VoucherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_voucher")
    private String id;

    @Column(name = "id_account")
    private String idAccount;

    @Column(name = "tx_alias")
    private String alias;

    @Column(name = "dt_revoked")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revoked;

    @Column(name = "dt_not_before")
    @Temporal(TemporalType.DATE)
    private Date notBefore;

    @Column(name = "dt_not_after")
    @Temporal(TemporalType.DATE)
    private Date notAfter;

    @Column(name = "dt_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    public VoucherModel(Voucher a) {
        this.id = a.id();
        this.alias = a.alias();
        this.idAccount = a.account() == null ? null : a.account().id();
        this.revoked = a.revoked();
        this.notBefore = a.notBefore();
        this.notAfter = a.notAfter();
        this.creation = a.creation();
    }

    public Voucher to() {
        return Voucher.builder()
            .id(this.id)
            .alias(this.alias)
            .account(AccountOut.builder().id(this.idAccount).build())
            .revoked(this.revoked)
            .notBefore(this.notBefore)
            .notAfter(this.notAfter)
            .creation(this.creation)
            .build();
    }

}
