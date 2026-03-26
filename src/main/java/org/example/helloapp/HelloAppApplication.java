package org.example.helloapp;


import lombok.RequiredArgsConstructor;
import org.example.helloapp.repository.UserRepository;
import org.example.helloapp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication

@RequiredArgsConstructor
public class HelloAppApplication implements CommandLineRunner  {


    private final TestService testService;

    public  static    void main(String[] args) {
        SpringApplication.run(HelloAppApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
//       System.err.println(testService.getMessage());
    }

}
