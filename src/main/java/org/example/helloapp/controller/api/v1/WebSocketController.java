package org.example.helloapp.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.ChatMessageDto;
import org.example.helloapp.service.IMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    private final IMessageService messageService;

    @MessageMapping("/private-message")
    public void sendPrivateMessage(ChatMessageDto message) {

        this.messageService.saveMessage(message.getSender(), message.getReceiver(), message.getContent());

        messagingTemplate.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );

    }
}
