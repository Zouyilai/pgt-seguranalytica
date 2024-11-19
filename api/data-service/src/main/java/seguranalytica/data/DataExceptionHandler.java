package seguranalytica.data;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException;

@ControllerAdvice
public class DataExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ProblemDetail> handle(Exception e) {
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(code, Arrays.toString(e.getStackTrace()));
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(code).body(problemDetail);
    }

    @ExceptionHandler(DataException.class)
    private ResponseEntity<ProblemDetail> handle(DataException e) {
        HttpStatus code = e.httpStatus();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(code, Arrays.toString(e.getStackTrace()));
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(code).body(problemDetail);
    }

    @ExceptionHandler(FeignException.class)
    private ResponseEntity<ProblemDetail> handle(FeignException e) {
        HttpStatus code = HttpStatus.valueOf(e.status());
        String msg = e.getMessage();
        String details = Arrays.toString(e.getStackTrace());
        String ref = "\"title\":\"";
        int idxi = msg.indexOf(ref);
        if (idxi > -1) {
            idxi += ref.length();
            details = msg;
            msg = msg.substring(idxi, msg.indexOf("\"", idxi));
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(code, details);
        problemDetail.setTitle(msg);
        return ResponseEntity.status(code).body(problemDetail);
    }
    
}