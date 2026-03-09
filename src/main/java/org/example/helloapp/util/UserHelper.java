package org.example.helloapp.util;

import org.example.helloapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserHelper {

    @Autowired
    private UserRepository userRepository;
    public String generateUniqueUsernameFromEmail(String email) {

        // Take part before @
        String base = email.split("@")[0]
                .toLowerCase()
                .replaceAll("\\s+", "")
                .replaceAll("[^a-z0-9]", "");

        String username;

        do {
            String random = UUID.randomUUID()
                    .toString()
                    .substring(0, 4);

            username = "@" + base + random;

        } while (userRepository.existsByUsername(username));

        return username;
    }
}
