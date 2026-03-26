package org.example.helloapp.controller.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.*;

import org.example.helloapp.service.IFileStorageService;
import org.example.helloapp.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;


    private final IFileStorageService fileStorageService;

    @PostMapping("/userBlock")
    public ResponseEntity<HttpResponseDto> userBlock(@RequestBody @Valid BlockRequestDto blockRequestDto) {
         HttpResponseDto response = new HttpResponseDto();

        try{

            Long userId = (Long) SecurityContextHolder
                    .getContext() // This returns the SecurityContext for the current user request.
                    .getAuthentication() // This returns the Authentication object for the current user request, which contains details about the authenticated user.
                    .getPrincipal(); // This returns the principal (user details) of the authenticated user, which is typically the user ID or username. In this case, it is expected to be the user ID as a Long.

            if(blockRequestDto.getGuestId().equals(userId)){
                throw new RuntimeException("You cannot block yourself");
            }
            this.userService.userBlockGuest(userId, blockRequestDto.getGuestId());

            response.setMessage("User blocked successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);


        } catch (RuntimeException e) {

            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getGuestByUserName")
    public ResponseEntity<HttpResponseDto> getGuestByUserName(@RequestParam String userName) {
        HttpResponseDto response = new HttpResponseDto();

        try{
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            GuestResponseDto guestResponseDto = this.userService.getGuestByUserName(userName, userId);

            response.setMessage("Guest user fetched successfully");
            response.setData(guestResponseDto);
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);


        } catch (RuntimeException e) {

            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUserByUserId")
    public ResponseEntity<HttpResponseDto> getUserByUserId() {
        HttpResponseDto response = new HttpResponseDto();
        try{
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            UserResponseDto userResponseDto = this.userService.getUserByUserId(userId);

            response.setMessage("User fetched successfully");
            response.setData(userResponseDto);
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
        } catch (RuntimeException e) {
                response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PutMapping("/prifileUpdate")
    public ResponseEntity<HttpResponseDto> profileUpdate(@RequestBody @Valid ProfileUpdateRequestDto requestDto) {


        HttpResponseDto response = new HttpResponseDto();
        try{
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            this.userService.userProfileUpdate(
                    userId,
                    requestDto.getName(),

                    requestDto.getAbout(),
                    requestDto.getPhone_number()
            );

            response.setMessage("User update successfully");
            response.setData(null);
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/profileImageUpload")
    public ResponseEntity<?> uploadProfile(
            @RequestParam("file") MultipartFile file)  {

        HttpResponseDto response = new HttpResponseDto();
        try{
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            String filePath = this.fileStorageService.storeFile(file, "profile");
            this.userService.userProfileImageUpdate(userId, filePath);

            response.setMessage("User profile image upload successfully");
            response.setData(null);
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
        } catch (IOException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/searchUsername")
    public ResponseEntity<HttpResponseDto> searchUsername(@RequestParam String username) {
        HttpResponseDto response = new HttpResponseDto();
        try{

            List<GuestResponseDto> guestResponseDtoList = this.userService.searchGuestWithUsername(username);

            response.setMessage("User fetched successfully");
            response.setData(guestResponseDtoList);
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

}

