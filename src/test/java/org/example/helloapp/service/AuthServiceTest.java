package org.example.helloapp.service;


import org.example.helloapp.dto.UserResponseDto;
import org.example.helloapp.models.User;
import org.example.helloapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AuthServiceTest {


    @Autowired
    private IAuthService authService;

    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    public void setUp() {
        // Set up any necessary test data or configurations here
    }


    @Test
    public void testOtpVerify() {

        System.out.println("Testing OTP verification...");
        UserResponseDto userResponseDto = this.authService.otpVerify("shivayan@gmail.com", "123456");
        assertTrue(userResponseDto.getEmail().equals("shivayan@gmail.com"));
    }
}
