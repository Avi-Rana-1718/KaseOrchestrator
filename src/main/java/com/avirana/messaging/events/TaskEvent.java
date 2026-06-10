package com.avirana.messaging.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEvent {
  Integer taskId;
  Integer pipelineId;
  Integer caseId;
  Integer step;
  Object payload;
  String remarks;
}
