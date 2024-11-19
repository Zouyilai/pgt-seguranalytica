package seguranalytica.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import seguranalytica.account.AccountIn;
import seguranalytica.account.LoginIn;

@RestController
public class AuthenticationResource implements AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ResponseEntity<ValidationOut> solveToken(ValidationIn in) {
        final Token token = authenticationService.solve(in.token());
        return ResponseEntity.ok().body(
            ValidationOut.builder()
                .id(token.id())
                .claims(token.claims())
                .build()
        );
    }

    @Override
    public ResponseEntity<AuthenticationOut> register(AccountIn in) {
        return responseToken(authenticationService.register(in));
    }

    @Override
    public ResponseEntity<AuthenticationOut> login(LoginIn in) {
        return responseToken(authenticationService.login(in));
    }

    private ResponseEntity<AuthenticationOut> responseToken(Token token) {
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(token.id())
                    .toUri()
            )
            .body(new AuthenticationOut(token.jwt()));
    }

}
