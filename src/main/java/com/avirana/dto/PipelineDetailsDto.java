package com.avirana.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PipelineDetailsDto {
  private String pipelineName;
  private String caseType;
  private String subType;
  private List<String> tasks;
}
