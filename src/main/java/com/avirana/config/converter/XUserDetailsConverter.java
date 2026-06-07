package com.avirana.config.converter;

import com.avirana.dto.XUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor
public class XUserDetailsConverter implements Converter<String, XUserDetails> {

  private final ObjectMapper objectMapper;

  @Override
  public XUserDetails convert(String userDetails) {
    try {
      return objectMapper.readValue(userDetails, XUserDetails.class);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Invalid X-User-Details header format");
    }
  }
}
