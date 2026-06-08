package com.avirana.entity;

import com.avirana.enums.CaseStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cases")
public class CaseEntity extends BaseEntity {

  @Column(name = "case_number", nullable = false, unique = true, updatable = false)
  private String caseNumber;

  @Column(name = "subject", nullable = false)
  private String subject;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CaseStatusEnum status;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "sub_type", nullable = false)
  private String subType;

  @Column(name = "org", nullable = false)
  private String org;

  private CustomerRef customerRef;
}
