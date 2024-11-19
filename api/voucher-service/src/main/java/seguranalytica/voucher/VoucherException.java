package seguranalytica.voucher;

import org.springframework.http.HttpStatus;

public class VoucherException extends RuntimeException {

    private final HttpStatus httpStatus;

    public VoucherException(HttpStatus httpStatus, Throwable e) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public VoucherException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

}
