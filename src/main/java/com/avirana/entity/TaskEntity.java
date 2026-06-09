package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity extends BaseEntity {

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "identifier", unique = true)
  private String identifier;
}
