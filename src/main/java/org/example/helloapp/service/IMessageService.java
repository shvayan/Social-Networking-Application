package org.example.helloapp.service;

import org.example.helloapp.dto.MessagesListDto;
import org.example.helloapp.dto.UserListWithMessageDto;
import org.example.helloapp.models.Message;

import java.util.List;

public interface IMessageService {

    void saveMessage(String sender, String receiver, String content);

    List<UserListWithMessageDto> getMessagesForUser(Long userId);

    List<MessagesListDto> getChatMessages(String username, Long userId);



}
