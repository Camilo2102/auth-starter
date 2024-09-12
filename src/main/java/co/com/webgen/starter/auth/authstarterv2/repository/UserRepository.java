package co.com.webgen.starter.auth.authstarterv2.repository;


import cloud.webgen.web.starter.sql.repository.AuditSqlRepository;
import co.com.webgen.starter.auth.authstarterv2.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AuditSqlRepository<User> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByMail(String mail);

    Optional<User> findByUserNameOrMail(String userName, String mail);
}
