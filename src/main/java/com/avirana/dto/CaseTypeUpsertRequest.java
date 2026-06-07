package com.avirana.dto;

import lombok.Getter;

@Getter
public class CaseTypeUpsertRequest {
  String name;
  Boolean isActive;
  Boolean isSubtype;
}
