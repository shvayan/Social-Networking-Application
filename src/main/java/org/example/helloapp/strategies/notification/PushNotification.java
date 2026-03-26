package org.example.helloapp.strategies.notification;


import lombok.RequiredArgsConstructor;
import org.example.helloapp.strategies.Notification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PushNotification implements Notification {
    @Override
    public void sendNotification(String recipient, String message, String subject) {

    }
}
