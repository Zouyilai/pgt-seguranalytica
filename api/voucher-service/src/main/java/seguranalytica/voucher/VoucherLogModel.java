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

@Entity
@Table(name = "voucher_log")
@AllArgsConstructor @NoArgsConstructor
public class VoucherLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_voucher_log")
    private String id;

    @Column(name = "id_voucher")
    private String idVoucher;

    @Column(name = "tx_origin")
    private String origin;

    @Column(name = "dt_used")
    @Temporal(TemporalType.TIMESTAMP)
    private Date used;

    public VoucherLogModel(VoucherLog a) {
        this.id = a.id();
        this.idVoucher = a.voucher().id();
        this.origin = a.origin();
        this.used = a.used();
    }

    public VoucherLog to() {
        return VoucherLog.builder()
            .id(this.id)
            .voucher(Voucher.builder().id(idVoucher).build())
            .origin(this.origin)
            .used(this.used)
            .build();
    }

}
