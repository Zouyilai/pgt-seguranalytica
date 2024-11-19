package seguranalytica.auth;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import seguranalytica.account.AccountController;
import seguranalytica.account.AccountIn;
import seguranalytica.account.AccountOut;
import seguranalytica.account.LoginIn;

@Service
public class AuthenticationService {

    @Value("${seguranalytica.jwt.duration}")
    private long duration = 1l;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AccountController accountController;

    public Token createToken(String idAccount, Map<String, String> claims) {
        Date notBefore = new Date();
        Date expiration = new Date(new Date().getTime() + duration);

        final Token token = tokenRepository.save(new TokenModel(new Token()
            .account(AccountOut.builder().id(idAccount).build())
            .issueAt(new Date())
            .notBefore(notBefore)
            .notAfter(expiration)
            .revoked(null)
            .claims(claims)
        )).to();

        return token.jwt(jwtService.create(token.id(), notBefore, expiration));
    }

    public Token solve(String jwt) {
        final String id = jwtService.getId(jwt);
        if (id == null) throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "token id not found");
        final Token token = tokenRepository.findById(id).orElse(null).to();
        if (token == null) throw new AuthenticationException(HttpStatus.NOT_FOUND, "token not found");
        if (token.account() == null) throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "there is not an account attached to token");
        token.claims().put("id-account", token.account().id());
        if (token.revoked() != null) throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "token revoked");
        if (token.notBefore().after(new Date())) throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "token is not active yet");
        if (token.notAfter().before(new Date())) throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "token expired");
        return token;
    }

    public AccountOut findByToken(String tokenId) {
        Token token = tokenRepository.findById(tokenId).map(TokenModel::to).orElse(null);
        return token == null ? null : token.account();
    }

    public Token register(AccountIn in) {
        AccountOut account = accountController.create(in).getBody();
        return createToken(account.id(), null);
    }

    public Token login(LoginIn in) {
        AccountOut account = accountController.login(in).getBody();
        return createToken(account.id(), null);
    }

}
