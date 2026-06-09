package com.avirana.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class AssociatedTaskDto {
  Integer taskId;
  Integer step;
  List<Integer> dependsOn;
}
