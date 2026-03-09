package org.example.helloapp.service;

import org.example.helloapp.dto.UserResponseDto;

public interface IAuthService {

     UserResponseDto sigun(String email);

     UserResponseDto otpVerify(String email, String otp);
}
