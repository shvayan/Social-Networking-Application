package org.example.helloapp.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.dto.*;

import org.example.helloapp.service.IMessageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final IMessageService messageService;



    @GetMapping("/get-chat-messages-list")
    public ResponseEntity<HttpResponseDto> getChatMessageList(@RequestParam String username) {
        // Implementation for fetching posts
        HttpResponseDto response = new HttpResponseDto();
        try {
            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            // Example: Fetch posts for a user (replace with actual user ID and pagination parameters)
            List<MessagesListDto> list  = this.messageService.getChatMessages(username, userId);
            response.setMessage("fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(list);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-user-chat-list")
    public ResponseEntity<HttpResponseDto> getUserChatList() {
        // Implementation for fetching posts
        HttpResponseDto response = new HttpResponseDto();
        try {


            Long userId = (Long) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            // Example: Fetch posts for a user (replace with actual user ID and pagination parameters)
            List<UserListWithMessageDto> list = this.messageService.getMessagesForUser(userId);
            response.setMessage("fetched successfully");
            response.setStatus(HttpStatus.OK);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(list);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}