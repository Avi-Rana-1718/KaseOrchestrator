package com.avirana.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XUserDetails {
  private Integer userId;
  private String email;
  private List<Map<String, Map<String, String>>> grants;
  private String org;
}
