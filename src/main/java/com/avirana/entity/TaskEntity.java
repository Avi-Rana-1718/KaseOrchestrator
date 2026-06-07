package com.avirana.entity;

import com.avirana.enums.TaskStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

  @Column(name = "case_id", nullable = false)
  Integer caseId;

  @Column(name = "status", nullable = false)
  TaskStatusEnum status;
}
