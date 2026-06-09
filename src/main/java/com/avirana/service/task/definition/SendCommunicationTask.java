package com.avirana.service.task.definition;

import com.avirana.dto.task.SendCommunicationPayload;
import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.events.TaskEvent;
import org.springframework.stereotype.Component;

@Component
public class SendCommunicationTask implements TaskDefinition {
  @Override
  public void execute(TaskEvent taskEvent) {
    SendCommunicationPayload payload = (SendCommunicationPayload) taskEvent.getPayload();

    System.out.println("Trigger comms");
  }

  @Override
  public TaskTypeEnum getType() {
    return TaskTypeEnum.SEND_COMMUNICATION;
  }
}
