package seguranalytica.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import seguranalytica.account.AccountIn;
import seguranalytica.account.LoginIn;

@FeignClient(name = "auth", url = "http://auth:8080")
public interface AuthenticationController {

    @PostMapping("/auth/token/solve")
    public ResponseEntity<ValidationOut> solveToken(@RequestBody ValidationIn in);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationOut> register(@RequestBody AccountIn in);

    @PostMapping("/login")
    public ResponseEntity<AuthenticationOut> login(@RequestBody LoginIn in);

}
