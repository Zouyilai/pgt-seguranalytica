package seguranalytica.auth;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import seguranalytica.account.AccountOut;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Accessors(fluent = true)
public class Token {

    private String id;
    private Date issueAt;
	private Date notBefore;
    private Date notAfter;
    private Date revoked;
    private AccountOut account;
	private Map<String, String> claims;

    private String jwt;

}