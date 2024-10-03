package espm.seguranalytics;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "usuario")
@Setter @Accessors(fluent = true)
@NoArgsConstructor
public class AccountModel {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nome")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String sha256;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date data_cadastro; //now
}