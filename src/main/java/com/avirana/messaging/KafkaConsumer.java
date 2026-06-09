package com.avirana.messaging;

import com.avirana.constants.KafkaTopics;
import com.avirana.messaging.events.TaskEvent;
import com.avirana.service.task.executor.TaskManager;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

  private final TaskManager taskManager;

  @KafkaListener(topics = KafkaTopics.TASK_READY, groupId = "task-consumer")
  public void taskReady(TaskEvent taskEvent) {
    taskManager.execute(taskEvent);
  }

  @KafkaListener(topics = KafkaTopics.TASK_COMPLETED, groupId = "task-consumer")
  public void taskCompleted(TaskEvent taskEvent) {
    taskManager.increment(taskEvent);
  }
}
