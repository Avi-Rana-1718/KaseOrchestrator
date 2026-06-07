package com.avirana.entity;

import com.avirana.enums.OwnerType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class CustomerRef {
  @Enumerated(EnumType.STRING)
  @Column(name = "owner_type", nullable = false)
  private OwnerType owner_type;

  private String owner_value;
}
