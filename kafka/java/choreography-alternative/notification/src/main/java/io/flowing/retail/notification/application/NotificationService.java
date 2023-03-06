package io.flowing.retail.notification.application;

import java.util.UUID;

import org.springframework.stereotype.Component;


@Component
public class NotificationService {

  public void sendEmail(String orderId, String message) {
    System.out.println("Send email for order " + orderId + " with message "+message);
    // TODO: send email
  }

}
