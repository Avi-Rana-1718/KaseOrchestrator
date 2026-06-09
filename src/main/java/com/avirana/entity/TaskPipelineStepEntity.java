package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_pipeline_steps")
@AllArgsConstructor
@NoArgsConstructor
public class TaskPipelineStepEntity extends BaseEntity {

  @Column(name = "task_id", nullable = false)
  private Integer taskId;

  @Column(name = "pipeline_id", nullable = false)
  private Integer pipelineId;

  @Column(name = "sequence_number", nullable = false)
  private Integer sequenceNumber;

  @Column(name = "depends_on", nullable = false)
  private List<Integer> dependsOn;
}
