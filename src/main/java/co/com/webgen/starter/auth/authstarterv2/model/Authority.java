package co.com.webgen.starter.auth.authstarterv2.model;

import co.com.webgen.starter.auth.authstarterv2.enums.AuthorityName;
import co.com.webgen.web.sql.starter.model.AuditSqlModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity(name = "authorities")
public class Authority extends AuditSqlModel {

    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    public Authority(AuthorityName name) {
        this.name = name;
    }
}
