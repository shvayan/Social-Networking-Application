package org.example.helloapp.service;


import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.GuestResponseDto;
import org.example.helloapp.dto.UserResponseDto;
import org.example.helloapp.models.BlockStatus;
import org.example.helloapp.models.Post;
import org.example.helloapp.models.User;
import org.example.helloapp.models.UserToUserSetting;
import org.example.helloapp.repository.UserRepository;
import org.example.helloapp.repository.UserToUserSettingRepository;

import org.example.helloapp.repository.searchStrategies.GenericSpecificationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{


    private final UserRepository userRepository;


    private final UserToUserSettingRepository userToUserSettingRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public UserResponseDto getUserByUserId(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setImageUrl(user.getImageUrl() != null ? baseUrl + "uploads/" + user.getImageUrl() : null);

        return new UserResponseDto().UserToDTO(user);
    }

    @Override
    public GuestResponseDto getGuestByUserName(String userName, Long userId) {

        User guestUser = this.userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GuestResponseDto responseDto = new GuestResponseDto();
        responseDto.UserToDTO(guestUser);
        responseDto.setGustId(guestUser.getId());

        // user -> guest
        Optional<UserToUserSetting> userBlockGuest =
                this.userToUserSettingRepository.findByUserAndAttemptUser(user, guestUser);

        // guest -> user (opposite)
        Optional<UserToUserSetting> guestBlockUser =
                this.userToUserSettingRepository.findByUserAndAttemptUser(guestUser, user);


        responseDto.setGuestUser(guestBlockUser);
        responseDto.setUser(userBlockGuest);


        return responseDto;
    }

    @Override
    public void userBlockGuest(Long userId, Long guestId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User guestUser = this.userRepository.findById(guestId).orElseThrow(() -> new RuntimeException("Guest user not found"));

        Optional<UserToUserSetting> userToUserSetting = this.userToUserSettingRepository.findByUserAndAttemptUser(user, guestUser);
        if(userToUserSetting.isPresent()){
            UserToUserSetting setting = userToUserSetting.get();

            if(setting.getBlockStatus() == BlockStatus.BLOCKED){
                 setting.setBlockStatus(BlockStatus.UNBLOCKED);
            }else {
                 setting.setBlockStatus(BlockStatus.BLOCKED);
            }

            this.userToUserSettingRepository.save(setting);
        }else {
            UserToUserSetting setting = new UserToUserSetting();
            setting.setUser(user);
            setting.setAttemptUser(guestUser);
            setting.setBlockStatus(BlockStatus.BLOCKED);
            this.userToUserSettingRepository.save(setting);
        }
    }

    @Override
    public void userProfileUpdate(Long userId, String name, String about, Long phone_number) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if(name != null){
            user.setName(name);
        }

        if(about != null){
            user.setAbout(about);
        }

        if(phone_number != null){
            user.setPhoneNumber(phone_number);
        }

        this.userRepository.save(user);
    }

    @Override
    public void userProfileImageUpdate(Long userId, String imageUrl) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String oldImageUrl = user.getImageUrl();
        if(oldImageUrl != null){
            String oldFilePath = "uploads/" + oldImageUrl;
            File oldFile = new File(oldFilePath);
            if (oldFile.exists()) {
               oldFile.delete();
            }
        }

        user.setImageUrl(imageUrl);
        this.userRepository.save(user);
    }

    @Override
    public List<GuestResponseDto> searchGuestWithUsername(String username) {


        List<User> users = this.userRepository.findByUsernameLike("%" + username + "%");


        System.err.println("Users found: " + users.size());

        List<GuestResponseDto> usersList = new ArrayList<>();

        for (User user : users){
            GuestResponseDto responseDto = new GuestResponseDto();
            responseDto.UserToDTO(user);
            responseDto.setGustId(user.getId());
            responseDto.setGuestUser(null);
            responseDto.setUser(null);
            usersList.add(responseDto);
        }

        return usersList;
    }


}
