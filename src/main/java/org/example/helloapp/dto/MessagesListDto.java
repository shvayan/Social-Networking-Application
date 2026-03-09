package org.example.helloapp.dto;

import lombok.Data;
import org.example.helloapp.models.Message;

import java.util.Date;

@Data
public class MessagesListDto {

    private String sender;
    private String receiver;
    private String content;
    private Date timestamp;

    public MessagesListDto convert(Message message){
        this.sender = message.getSender().getUsername();
        this.receiver = message.getReceiver().getUsername();
        this.content = message.getContent();
        this.timestamp = message.getCreated_at();
        return this;
    }
}
