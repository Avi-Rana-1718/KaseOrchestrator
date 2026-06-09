package com.avirana.service.task.definition;

import com.avirana.enums.TaskTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class SendCommunicationTask implements TaskDefinition {
  @Override
  public void execute(Object payload) {
    // do nothing for now
  }

  @Override
  public TaskTypeEnum getType() {
    return TaskTypeEnum.SEND_COMMUNICATION;
  }
}
