package com.avirana.service.task.definition;

import com.avirana.dto.task.WebhookPayload;
import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.events.TaskEvent;
import org.springframework.stereotype.Component;

@Component
public class WebhookTask implements TaskDefinition {
  @Override
  public boolean execute(TaskEvent payload) {
    WebhookPayload webhookPayload = (WebhookPayload) payload.getPayload();

    return true;
  }

  @Override
  public TaskTypeEnum getType() {
    return TaskTypeEnum.WEBHOOK;
  }
}
