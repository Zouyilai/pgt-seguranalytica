package seguranalytica.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AccountResource implements AccountController {

    @Autowired
    private AccountService accountService;

    public ResponseEntity<AccountOut> create(AccountIn in) {
        final Account account = accountService.create(AccountParser.to(in));
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.id())
                .toUri())
            .body(AccountParser.to(account));
    }

    public ResponseEntity<AccountOut> whoIAm(String idAccount) {
        final AccountOut account = AccountParser.to(accountService.findById(idAccount));
        if (account == null) throw new AccountException(HttpStatus.NOT_FOUND, "accound [" + idAccount + "] not found.");
        return ResponseEntity.ok().body(account);
    }

    @Override
    public ResponseEntity<AccountOut> login(LoginIn in) {
        final AccountOut account = AccountParser.to(accountService.findByEmailAndPassword(in.email(), in.password()));
        if (account == null) throw new AccountException(HttpStatus.NOT_FOUND, "accound [" + in.email() + "] not found.");
        return ResponseEntity.ok().body(account);
    }
    
}
