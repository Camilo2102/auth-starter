package co.com.webgen.starter.auth.authstarterv2.repository;

import co.com.webgen.starter.auth.authstarterv2.enums.AuthorityName;
import co.com.webgen.starter.auth.authstarterv2.model.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {
    Optional<Authority> findByName(AuthorityName name);
}
