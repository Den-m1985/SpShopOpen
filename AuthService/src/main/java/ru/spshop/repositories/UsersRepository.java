package ru.spshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spshop.model.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    /**
     * Selects a user by his name.
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * Selects a user by his name.
     * @return
     */
    Optional<User> findByEmail(String email);
}
