package co.com.webgen.starter.auth.authstarterv2.model;


import cloud.webgen.web.starter.sql.model.AuditSqlModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User extends AuditSqlModel {

    private Boolean active;

    @Column(unique = true)
    private String mail;

    @Column(unique = true)
    private String userName;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

}
