package io.flowing.retail.notification.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.handler.annotation.Header;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.flowing.retail.notification.application.NotificationService;

@Component
public class MessageListener {
    public static final String TOPIC_NAME = "flowing-retail";
  @Autowired
  private NotificationService notificationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "notification", topics = TOPIC_NAME)
  public void orderUpdated(String messageJson, @Header("type") String messageType) throws Exception {
      // System.out.println("Received message: " + messageType);
      if ("OrderPlacedEvent".equals(messageType) ||
              "PaymentReceivedEvent".equals(messageType) ||
              "GoodsShippedEvent".equals(messageType)) {
          JsonNode message = objectMapper.readTree(messageJson);
          String id = message.get("id").asText();
          notificationService.sendEmail(id, "New Order status: " + messageType.split("Event")[0]);
      }
  }
    
}
