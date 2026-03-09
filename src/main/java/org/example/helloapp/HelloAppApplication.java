package org.example.helloapp;

import org.example.helloapp.models.User;
import org.example.helloapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.crypto.Cipher;
import java.security.*;
@SpringBootApplication
public class HelloAppApplication implements CommandLineRunner  {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloAppApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }
}
