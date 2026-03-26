package org.example.helloapp.strategies;


import lombok.RequiredArgsConstructor;
import org.example.helloapp.strategies.notification.EmailNotification;
import org.example.helloapp.strategies.notification.PushNotification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationStrategy {
    private final EmailNotification emailNotification;

    private final PushNotification pushNotification;

    public Notification sendNotification(String type) {

        return switch (type) {
            case "email" -> emailNotification;
            case "push" -> pushNotification;
            default -> throw new IllegalArgumentException("Invalid notification type: " + type);
        };
    }
}
