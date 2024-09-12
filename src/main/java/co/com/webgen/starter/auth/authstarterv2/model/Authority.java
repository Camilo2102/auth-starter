package co.com.webgen.starter.auth.authstarterv2.model;

import cloud.webgen.web.starter.sql.model.AuditSqlModel;
import co.com.webgen.starter.auth.authstarterv2.enums.AuthorityName;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "authorities")
public class Authority extends AuditSqlModel {

    @Enumerated(EnumType.STRING)
    private AuthorityName name;
}
