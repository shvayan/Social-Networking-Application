package org.example.helloapp.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity(name = "users")
public class User extends BaseModel {

    private String name;

    private String username;

    private String email;

    private String otp;

    private Long otp_expiry;

    private String private_key;

    private String public_key;

    private String about;

    private Long phoneNumber;



    @OneToMany(mappedBy = "sender")
    @JsonManagedReference
    private List<Message> lastMessage;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Post> postList;

    private String imageUrl;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private UserToUserSetting userToUserSetting;
}
