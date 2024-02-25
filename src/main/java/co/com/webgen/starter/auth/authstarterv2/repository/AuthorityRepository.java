package co.com.webgen.starter.auth.authstarterv2.repository;

import co.com.webgen.starter.auth.authstarterv2.enums.AuthorityName;
import co.com.webgen.starter.auth.authstarterv2.model.Authority;
import co.com.webgen.web.sql.starter.repository.AuditSqlRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends AuditSqlRepository<Authority> {
    Optional<Authority> findByName(AuthorityName name);
}
