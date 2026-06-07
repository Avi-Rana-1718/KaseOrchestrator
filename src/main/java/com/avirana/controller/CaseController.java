package com.avirana.controller;

import com.avirana.dto.CaseCreationRequest;
import com.avirana.dto.CaseTypeUpsertRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.service.CaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("case")
@AllArgsConstructor
public class CaseController {

  private final CaseService caseService;

  @GetMapping("/types")
  public ResponseEntity<List<String>> getCaseTypes(
      @NotNull @RequestHeader("X-User-Details") XUserDetails userDetails) {
    return ResponseEntity.ok(caseService.getAllCaseType(userDetails, false));
  }

  @GetMapping("/subtypes")
  public ResponseEntity<List<String>> getSubTypes(
      @NotNull @RequestHeader("X-User-Details") XUserDetails userDetails) {
    return ResponseEntity.ok(caseService.getAllCaseType(userDetails, true));
  }

  @PutMapping("/upsertType")
  public ResponseEntity<String> createCaseType(
      @Valid @RequestBody CaseTypeUpsertRequest request,
      @NotNull @RequestHeader("X-User-Details") XUserDetails userDetails) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(caseService.upsertCaseType(request, userDetails));
  }

  @PostMapping("/caseCreation")
  public ResponseEntity<String> caseCreation(
      @Valid @RequestBody CaseCreationRequest request,
      @NotNull @RequestHeader("X-User-Details") XUserDetails userDetails)
      throws BadRequestException {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(caseService.createCase(request, userDetails));
  }
}
