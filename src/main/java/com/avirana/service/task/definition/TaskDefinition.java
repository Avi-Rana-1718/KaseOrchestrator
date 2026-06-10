package com.avirana.service.task.definition;

import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.events.TaskEvent;

public interface TaskDefinition {
  public boolean execute(TaskEvent payload);

  public TaskTypeEnum getType();
}
