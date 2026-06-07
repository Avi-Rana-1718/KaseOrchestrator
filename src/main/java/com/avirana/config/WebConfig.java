package com.avirana.config;

import com.avirana.config.converter.XUserDetailsConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final XUserDetailsConverter userDetailsConverter;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(userDetailsConverter);
  }
}
