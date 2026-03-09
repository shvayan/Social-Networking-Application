package org.example.helloapp.dto;


import lombok.Data;
import org.example.helloapp.models.User;

@Data
public class UserResponseDto {



    private String username;
    private String token;
    private String email;
    private String public_key;
    private String imageUrl;
    private String about;
    private Long phone_number;
    private String name;



    public UserResponseDto UserToDTO(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.public_key = user.getPublic_key();
        this.about = user.getAbout();
        this.imageUrl = user.getImageUrl();
        this.name = user.getName();

        this.phone_number = user.getPhoneNumber();
        return this;
    }
}
