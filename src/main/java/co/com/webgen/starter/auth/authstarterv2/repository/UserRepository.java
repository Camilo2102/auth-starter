package co.com.webgen.starter.auth.authstarterv2.repository;


import co.com.webgen.starter.auth.authstarterv2.model.User;
import co.com.webgen.web.sql.starter.repository.AuditSqlRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AuditSqlRepository<User> {
    Optional<User> findByUserName(String userName);
}
