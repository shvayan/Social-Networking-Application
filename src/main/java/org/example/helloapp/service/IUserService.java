package org.example.helloapp.service;

import org.example.helloapp.dto.GuestResponseDto;
import org.example.helloapp.dto.UserResponseDto;

import java.util.List;

public interface IUserService {
    UserResponseDto getUserByUserId(Long userId);

    GuestResponseDto getGuestByUserName(String userName, Long userId);

    void userBlockGuest(Long userId, Long guestId);

    void userProfileUpdate(Long userId, String name, String about, Long phone_number);

    void userProfileImageUpdate(Long userId, String imageUrl);

    List<GuestResponseDto> searchGuestWithUsername(String username);

}
