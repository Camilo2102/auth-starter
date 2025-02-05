package co.com.webgen.starter.auth.authstarterv2.repository;


import co.com.webgen.starter.auth.authstarterv2.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByMailAndOwnerId(String mail, String ownerId);

    Optional<User> findByOwnerIdAndUserNameOrMail(String ownerId, String userName, String mail);

}
