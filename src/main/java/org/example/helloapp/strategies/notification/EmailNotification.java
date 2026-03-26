package org.example.helloapp.strategies.notification;

import org.example.helloapp.clients.KafkaProducerClient;
import org.example.helloapp.dto.EmailDto;
import org.example.helloapp.strategies.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class EmailNotification implements Notification {



    private final ObjectMapper objectMapper;

    private final KafkaProducerClient kafkaProducerClient;

    @Override
    public void sendNotification(String recipient, String body, String subject) {
        EmailDto emailDto = new EmailDto();
        emailDto.setSender(recipient);
        emailDto.setSubject(subject);
        emailDto.setBody(body);

        kafkaProducerClient.sendMessage("Signup",
                objectMapper.writeValueAsString(emailDto));
    }
}
