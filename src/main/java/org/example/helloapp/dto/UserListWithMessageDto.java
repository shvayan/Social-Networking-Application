package org.example.helloapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserListWithMessageDto {
    private String username;
    private String lastMessageContent;
    private String name;
    private Date lastMessageTimestamp;
    private String imageUrl;
    private String type; // "sent" or "received"
}
