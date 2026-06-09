package com.avirana.controller;

import com.avirana.constants.CommonHeaders;
import com.avirana.dto.PipelineCreationRequest;
import com.avirana.dto.PipelineDetailsDto;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.TaskPipelineEntity;
import com.avirana.service.PipelineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("pipelines")
@RequiredArgsConstructor
public class PipelineController {
  private final PipelineService pipelineService;

  @GetMapping
  public ResponseEntity<List<TaskPipelineEntity>> getAllPipelines(
      @NotNull @RequestHeader(CommonHeaders.XUserDetails) XUserDetails userDetails) {
    return ResponseEntity.ok(pipelineService.getAllPipelines(userDetails));
  }

  @PostMapping
  public ResponseEntity<String> createPipeline(
      @Valid @RequestBody PipelineCreationRequest pipelineCreationRequest,
      @NotNull @RequestHeader(CommonHeaders.XUserDetails) XUserDetails userDetails)
      throws BadRequestException {
    System.out.println(pipelineCreationRequest.getCaseType());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(pipelineService.createPipeline(pipelineCreationRequest, userDetails));
  }

  @GetMapping("/details")
  public ResponseEntity<PipelineDetailsDto> getDetails(
      @NotNull @RequestParam("pipelineId") Integer pipelineId) {
    return ResponseEntity.ok(pipelineService.getPipelineDetails(pipelineId));
  }
}
