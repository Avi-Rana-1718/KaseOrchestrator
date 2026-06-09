package com.avirana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KaseOrchestrator {
  public static void main(String[] args) {
    SpringApplication.run(KaseOrchestrator.class, args);
  }
}
