package co.com.webgen.starter.auth.authstarterv2.repository;

import cloud.webgen.web.starter.sql.repository.AuditSqlRepository;
import co.com.webgen.starter.auth.authstarterv2.enums.AuthorityName;
import co.com.webgen.starter.auth.authstarterv2.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends AuditSqlRepository<Authority> {
    Optional<Authority> findByName(AuthorityName name);
}
