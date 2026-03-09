package org.example.helloapp.service;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.UserResponseDto;
import org.example.helloapp.models.User;
import org.example.helloapp.repository.UserRepository;
import org.example.helloapp.util.JwtHelper;
import org.example.helloapp.util.UserHelper;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{


    private final UserRepository userRepository;


    private final JwtHelper jwtHelper;


    private final UserHelper userHelper;

    @Override
    public UserResponseDto sigun(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        Random random = new Random();
        Long currentTime = System.currentTimeMillis() + 5 * 60 * 1000;
        String otp = String.format("%06d", random.nextInt(1000000));

        if(userOptional.isEmpty()){
            User user = new User();
            user.setEmail(email);
            user.setOtp_expiry(currentTime); // OTP expires in 5 minutes
            user.setOtp(otp); // 6 digit OTP
            userRepository.save(user);

            UserResponseDto responseDto = new UserResponseDto();
            responseDto = responseDto.UserToDTO(user);
            return responseDto;
        }else{
            User user = userOptional.get();

            user.setOtp_expiry(currentTime); // OTP expires in 5 minutes
            user.setOtp(otp); // 6 digit OTP
            userRepository.save(user);

            UserResponseDto responseDto = new UserResponseDto();
            responseDto = responseDto.UserToDTO(user);
            return responseDto;
        }
    }

    @Override
    public UserResponseDto otpVerify(String email, String otp) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }else{
            User user = userOptional.get();

//            if(user.getOtp_expiry() == null || user.getOtp_expiry() < System.currentTimeMillis()){
//                throw new RuntimeException("OTP expired");
//            }

//            if(user.getOtp() == null || !user.getOtp().equals(otp)){
//                throw new RuntimeException("Invalid OTP");
//            }
//
            if(user.getUsername() == null){
                String username = userHelper.generateUniqueUsernameFromEmail(user.getEmail());
                user.setUsername(username);
            }

            user.setOtp(null);
            user.setOtp_expiry(null);

            this.userRepository.save(user);

            String token = jwtHelper.generatedTokenForUser(user.getId());

            UserResponseDto responseDto = new UserResponseDto();
            responseDto = responseDto.UserToDTO(user);
            responseDto.setToken(token);
            return responseDto;
        }
    }


}
