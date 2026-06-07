package com.avirana.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "case_type_master")
public class CaseTypeEntity extends BaseEntity {
  @Column(name = "type")
  String name;

  @Column(name = "is_subtype")
  Boolean isSubtype;

  @Column(name = "org")
  String org;
}
