package org.example.helloapp.service;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.MessagesListDto;
import org.example.helloapp.dto.UserListWithMessageDto;
import org.example.helloapp.models.Message;
import org.example.helloapp.models.User;
import org.example.helloapp.repository.MessageRepository;
import org.example.helloapp.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService{

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    public void saveMessage(String sender, String receiver, String content) {
        User senderUser = this.userRepository.findByUsername(sender).orElseThrow(() -> new RuntimeException("Sender user not found"));
        User receiverUser = this.userRepository.findByUsername(receiver).orElseThrow(() -> new RuntimeException("Receiver user not found"));
        Message message = new Message();
        message.setSender(senderUser);
        message.setReceiver(receiverUser);
        message.setContent(content);
        this.messageRepository.save(message);

    }

    @Override
    public List<UserListWithMessageDto> getMessagesForUser(Long userId) {

        List<Message> userMessages = this.messageRepository.findChatList(userId);



        List<UserListWithMessageDto> userListWithMessageDtos = new ArrayList<>();




        for (Message message : userMessages) {
            UserListWithMessageDto dto = new UserListWithMessageDto();
            if(message.getSender().getId().equals(userId)){
                dto.setType("sent");
                dto.setUsername(message.getReceiver().getUsername());
                dto.setImageUrl(message.getReceiver().getImageUrl());
            }else{
                dto.setType("received");
                dto.setImageUrl(message.getSender().getImageUrl());
                dto.setUsername(message.getSender().getUsername());
            }
            dto.setLastMessageContent(message.getContent());
            dto.setLastMessageTimestamp(message.getCreated_at());
            userListWithMessageDtos.add(dto);
        }

        return userListWithMessageDtos;
    }

    @Override
    public List<MessagesListDto> getChatMessages(String username, Long userId) {

        User senderUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        User receiverUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Receiver user not found"));

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        List<Message> sendMessages =
                this.messageRepository.findBySenderIdAndReceiverId(
                        senderUser.getId(),
                        receiverUser.getId(),
                        sort
                );

        List<Message> receiveMessages =
                this.messageRepository.findBySenderIdAndReceiverId(
                        receiverUser.getId(),
                        senderUser.getId(),
                        sort
                );

        // Merge both message lists
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sendMessages);
        allMessages.addAll(receiveMessages);

        // Sort again by id to keep proper chat order
        allMessages.sort(Comparator.comparing(Message::getId));

        List<MessagesListDto> messagesListDto = new ArrayList<>();

        for (Message message : allMessages) {

            MessagesListDto messageDto = new MessagesListDto();
            messageDto.setContent(message.getContent());
            messageDto.setSender(message.getSender().getUsername());
            messageDto.setReceiver(message.getReceiver().getUsername());
            messageDto.setTimestamp(message.getCreated_at());

            messagesListDto.add(messageDto);
        }

        return messagesListDto;
    }
}
