package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_step_history")
public class TaskStepHistoryEntity extends BaseEntity {

  @Column(name = "task_id", nullable = false)
  private Integer taskId;

  @Column(name = "pipeline_id", nullable = false)
  private Integer pipelineId;

  @Column(name = "case_id", nullable = false)
  private Integer caseId;

  @Column(name = "sequence_number", nullable = false)
  private Integer sequenceNumber;

  @Column(name = "payload", nullable = true)
  private String payload;

  @Column(name = "remarks")
  private String remarks;
}
