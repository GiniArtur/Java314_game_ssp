package ru.artgin.game.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artgin.game.enums.Status;
import ru.artgin.game.models.User;

import java.util.List;


/**
 *It is the repository interface for work with user data.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String  username);
    List<User> findAll();
    boolean existsBy();
    boolean existsByName(String name);
    List<User> findUserByStatus(Status status);


}
