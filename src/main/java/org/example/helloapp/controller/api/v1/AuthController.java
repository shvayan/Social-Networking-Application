package org.example.helloapp.controller.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.HttpResponseDto;
import org.example.helloapp.dto.OtpRequestDto;
import org.example.helloapp.dto.UserResponseDto;
import org.example.helloapp.dto.UserRequestDto;
import org.example.helloapp.service.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<HttpResponseDto> signup(@Valid @RequestBody UserRequestDto requestDto) {
        HttpResponseDto response = new HttpResponseDto();
        try{

            UserResponseDto user = authService.sigun(requestDto.getEmail());

            response.setMessage("User created successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(user);

        } catch (RuntimeException e) {

            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/otp-verify")
    public ResponseEntity<HttpResponseDto> otpVerify(@Valid @RequestBody OtpRequestDto requestDto) {
        HttpResponseDto response = new HttpResponseDto();
        try {
            UserResponseDto user = authService.otpVerify(requestDto.getEmail(), requestDto.getOtp());

            response.setMessage("OTP verified successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(user);


        } catch (RuntimeException e) {
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
        return ResponseEntity.ok(response);
    }
}
