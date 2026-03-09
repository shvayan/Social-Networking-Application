package org.example.helloapp.repository;

import org.example.helloapp.models.User;
import org.example.helloapp.models.UserToUserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserToUserSettingRepository extends JpaRepository<UserToUserSetting,Long> {
    Optional<UserToUserSetting> findByUserAndAttemptUser(User user, User attemptUser);
}
