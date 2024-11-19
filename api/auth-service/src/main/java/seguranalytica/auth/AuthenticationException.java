package seguranalytica.auth;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AuthenticationException(HttpStatus httpStatus, Throwable e) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public AuthenticationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

}
