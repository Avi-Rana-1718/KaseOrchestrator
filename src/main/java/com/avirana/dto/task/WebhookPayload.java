package com.avirana.dto.task;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class WebhookPayload {
  HttpMethod method;
  String url;
  Map<String, String> headers;
  Object body;
}
