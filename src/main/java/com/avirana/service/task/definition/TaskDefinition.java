package com.avirana.service.task.definition;

import com.avirana.enums.TaskTypeEnum;

public interface TaskDefinition {
  public void execute(Object payload);

  public TaskTypeEnum getType();
}
