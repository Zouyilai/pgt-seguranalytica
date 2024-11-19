package seguranalytica.account;

import org.springframework.http.HttpStatus;

public class AccountException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AccountException(HttpStatus httpStatus, Throwable e) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public AccountException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

}
