package com.avirana.util;

import com.avirana.repository.CaseTypeRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CaseUtil {

  private final CaseTypeRepository caseTypeRepository;

  public void validateTypeAndSubtype(String type, String subType, String org)
      throws BadRequestException {
    if (Objects.isNull(
        caseTypeRepository.findByOrgAndNameAndIsSubtypeAndIsActiveTrue(org, type, false))) {
      throw new BadRequestException("CaseType is not valid");
    }

    if (Objects.isNull(
        caseTypeRepository.findByOrgAndNameAndIsSubtypeAndIsActiveTrue(org, subType, true))) {
      throw new BadRequestException("SubType is not valid");
    }
  }
}
