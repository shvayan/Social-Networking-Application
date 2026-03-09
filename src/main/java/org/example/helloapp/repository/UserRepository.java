package org.example.helloapp.repository;

import org.example.helloapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String userName);

    boolean existsByUsername(String username);

    List<User> findByUsernameLike(String username);
}
