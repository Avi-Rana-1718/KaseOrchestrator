package com.avirana.dto;

import lombok.Getter;

@Getter
public class CaseCreationRequest {
  String subject;
  String caseType;
  String subType;
  String caseNumber;
}
