package com.avirana.service.task.definition;

import com.avirana.dto.task.SendCommunicationPayload;
import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.events.TaskEvent;
import org.springframework.stereotype.Component;

@Component
public class SendCommunicationTask implements TaskDefinition {
  @Override
  public boolean execute(TaskEvent taskEvent) {
    SendCommunicationPayload payload = (SendCommunicationPayload) taskEvent.getPayload();

    System.out.println("Trigger comms");

    return true;
  }

  @Override
  public TaskTypeEnum getType() {
    return TaskTypeEnum.SEND_COMMUNICATION;
  }
}
