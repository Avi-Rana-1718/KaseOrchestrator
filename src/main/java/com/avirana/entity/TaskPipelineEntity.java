package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_pipelines")
public class TaskPipelineEntity extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "sub_type", nullable = false)
  private String subType;

  @Column(name = "org", nullable = false)
  private String org;
}
