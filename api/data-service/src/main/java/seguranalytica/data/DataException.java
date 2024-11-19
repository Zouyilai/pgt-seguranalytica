package seguranalytica.data;

import org.springframework.http.HttpStatus;

public class DataException extends RuntimeException {

    private final HttpStatus httpStatus;

    public DataException(HttpStatus httpStatus, Throwable e) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public DataException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

}
