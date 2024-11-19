package seguranalytica.auth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import seguranalytica.account.AccountOut;

import java.util.Date;
import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "token")
@EqualsAndHashCode(of = "id")
@Setter @Accessors(chain = true, fluent = true)
@AllArgsConstructor @NoArgsConstructor
public class TokenModel {
	
    @Id
    @Column(name = "id_token")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

	private String idAccount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtIssueAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNotBefore;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNotAfter;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtRevoked;
	
	public TokenModel(Token v) {
		this.id = v.id();
		this.idAccount = v.account() == null ? null : v.account().id();
		this.dtIssueAt = v.issueAt();
		this.dtNotBefore = v.notBefore();
		this.dtNotAfter = v.notAfter();
	}

	public Token to() {
		return Token.builder()
			.id(id)
			.account(idAccount == null ? null : AccountOut.builder().id(idAccount).build())
			.claims(new HashMap<>())
			.issueAt(dtIssueAt)
			.notBefore(dtNotBefore)
			.notAfter(dtNotAfter)
            .build();
	}	

}