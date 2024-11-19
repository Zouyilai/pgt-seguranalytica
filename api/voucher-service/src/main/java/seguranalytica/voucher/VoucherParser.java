package seguranalytica.voucher;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;

public class VoucherParser {

    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfDatetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static final Voucher to(VoucherIn in) {
        try {
            return in == null ? null :
                Voucher.builder()
                    .alias(in.alias())
                    .notBefore(in.notBefore() == null ? null : sdfDate.parse(in.notBefore()))
                    .notAfter(in.notAfter() == null ? null : sdfDate.parse(in.notAfter()))
                    .build();
        } catch (ParseException e) {
            throw new VoucherException(HttpStatus.BAD_REQUEST, e);
        }
    }

    public static final VoucherOut to(Voucher a) {
        return a == null ? null :
            VoucherOut.builder()
                .id(a.id())
                .alias(a.alias())
                .notBefore(a.notBefore() == null ? null : sdfDate.format(a.notBefore()))
                .notAfter(a.notAfter() == null ? null : sdfDate.format(a.notAfter()))
                .revoked(a.revoked() == null ? null : sdfDatetime.format(a.revoked()))
                .numberOfDownloads(a.numberOfDownloads())
                .useds(a.useds() == null ? null : a.useds().stream().map(VoucherParser::to).toList())
                .creation(a.creation() == null ? null : sdfDatetime.format(a.creation()))
                .build();
    }

    public static final VoucherLogOut to(VoucherLog l) {
        return l == null ? null:
            VoucherLogOut.builder()
                .id(l.id())
                .used(l.used() == null ? null : sdfDatetime.format(l.used()))
                .origin(l.origin())
                .build();
    }
    
}
