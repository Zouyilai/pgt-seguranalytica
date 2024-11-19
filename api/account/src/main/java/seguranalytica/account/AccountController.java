package seguranalytica.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account", url = "http://account:8080")
public interface AccountController {

    @PostMapping("/account/create")
    ResponseEntity<AccountOut> create(
        @RequestBody(required = true) AccountIn in
    );

    @PostMapping("/account/login")
    ResponseEntity<AccountOut> login(
        @RequestBody(required = true) LoginIn in
    );

    @GetMapping("/account")
    ResponseEntity<AccountOut> whoIAm(
        @RequestHeader(value = "id-account", required = true) String idAccount
    );

}
