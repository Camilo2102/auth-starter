package co.com.webgen.starter.auth.authstarterv2.model;

import co.com.webgen.web.sql.starter.model.AuditSqlModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User extends AuditSqlModel {

    @Column(unique = true)
    private String userName;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public User(String userName, String password, List<Authority> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }
}
