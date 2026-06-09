package com.avirana.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String eventName, Object payload) {
    kafkaTemplate.send(eventName, payload);
  }
}
