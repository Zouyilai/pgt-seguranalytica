package seguranalytica.account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account create(Account account) {

        if (account.password() == null) {
            throw new AccountException(HttpStatus.BAD_REQUEST, "password is empty");
        }
        account.password(account.password().trim());
        if (account.password().length() == 0) {
            throw new AccountException(HttpStatus.BAD_REQUEST, "password is empty");
        }

        if (accountRepository.findByEmail(account.email()).isPresent()) {
            throw new AccountException(HttpStatus.BAD_REQUEST, "email have been registered");
        }

        account.sha256(calcSha256b64(account.password()));
        account.password("");
        return accountRepository.save(new AccountModel(account)).to();
    }

    public Account findById(String id) {
        return accountRepository.findById(id).map(AccountModel::to).orElse(null);
    }

    public Account findByEmailAndPassword(String email, String password) {
        Account account = accountRepository.findByEmail(email).map(AccountModel::to).orElse(null);
        if (account == null) {
            throw new AccountException(HttpStatus.UNAUTHORIZED, "email not found");
        }
        if (password == null || password.trim().length() == 0) {
            throw new AccountException(HttpStatus.BAD_REQUEST, "password is empty");
        }
        final String sha256 = calcSha256b64(password.trim());
        if (!sha256.equals(account.sha256())) {
            throw new AccountException(HttpStatus.UNAUTHORIZED, "password not match");
        }
        return account;
    }

    private String calcSha256b64(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded;
        } catch (Exception e) {
            throw new AccountException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
    
}
