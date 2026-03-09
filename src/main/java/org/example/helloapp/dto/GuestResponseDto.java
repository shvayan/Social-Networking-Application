package org.example.helloapp.dto;


import lombok.Data;
import org.example.helloapp.models.User;

@Data
public class GuestResponseDto {

    private String username;

    private String email;

    private String name;

    private String imageUrl;
    private String about;
    private boolean isBlocked = false;
    private Long gustId;

    private Object guestUser;
    private Object user;


    public GuestResponseDto UserToDTO(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.about = user.getAbout();
        this.name = user.getName();

        if(user.getImageUrl() == null){
            this.imageUrl = "image.jpg";
        }else{
            this.imageUrl = user.getImageUrl();
        }


        return this;
    }
}
