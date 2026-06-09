package com.avirana.dto.task;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendCommunicationPayload {
  String channel;
  String payload;
  String contactAddress;
  String title;

  public void validate() {
    if (Objects.isNull(channel)
        || Objects.isNull(payload)
        || Objects.isNull(contactAddress)
        || Objects.isNull(title)) {
      throw new IllegalArgumentException("Invalid event");
    }
  }
}
