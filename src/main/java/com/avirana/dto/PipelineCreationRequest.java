package com.avirana.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Getter
public class PipelineCreationRequest {
  @NotBlank private String name;
  @NotBlank private String caseType;
  @NotBlank private String subType;
  @NotNull private List<AssociatedTaskDto> tasks;
}
