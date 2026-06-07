package com.avirana.service;

import com.avirana.dto.CaseCreationRequest;
import com.avirana.dto.CaseTypeUpsertRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.CaseEntity;
import com.avirana.entity.CaseTypeEntity;
import com.avirana.enums.CaseStatusEnum;
import com.avirana.repository.CaseRepository;
import com.avirana.repository.CaseTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CaseService {

  private final CaseTypeRepository caseTypeRepository;
  private final CaseRepository caseRepository;

  public List<String> getAllCaseType(XUserDetails userDetails, Boolean subType) {
    List<CaseTypeEntity> typeEntityList =
        caseTypeRepository.findAllByOrgAndIsActiveTrueAndIsSubtype(userDetails.getOrg(), subType);
    List<String> typeList = new ArrayList<>();

    typeEntityList.forEach(
        e -> {
          typeList.add(e.getName());
        });

    return typeList;
  }

  public String upsertCaseType(CaseTypeUpsertRequest request, XUserDetails userDetails) {
    CaseTypeEntity typeEntity =
        caseTypeRepository.findByNameAndOrgAndIsSubtype(
            request.getName(), userDetails.getOrg(), request.getIsSubtype());

    if (Objects.isNull(typeEntity)) {
      typeEntity = new CaseTypeEntity();
      typeEntity.setOrg(userDetails.getOrg());
      typeEntity.setName(request.getName());
      typeEntity.setIsSubtype(request.getIsSubtype());
    }

    typeEntity.setIsActive(request.getIsActive());

    caseTypeRepository.save(typeEntity);

    return "Upserted case type master";
  }

  public String createCase(CaseCreationRequest request, XUserDetails userDetails)
      throws BadRequestException {

    String org = userDetails.getOrg();
    String caseType = request.getCaseType();
    String subject = request.getSubject();
    String subType = request.getSubType();

    validateTypeAndSubtype(caseType, subType, org);

    CaseEntity caseEntity = new CaseEntity();
    caseEntity.setOrg(org);
    caseEntity.setStatus(CaseStatusEnum.NEW);
    caseEntity.setSubject(subject);
    caseEntity.setType(caseType);
    caseEntity.setSubType(subType);

    caseEntity = caseRepository.save(caseEntity);

    return "Created case with ID: " + caseEntity.getId();
  }

  private void validateTypeAndSubtype(String type, String subType, String org)
      throws BadRequestException {
    if (Objects.isNull(
        caseTypeRepository.findByNameAndIsSubtypeAndOrgAndIsActiveTrue(type, false, org))) {
      throw new BadRequestException("CaseType is not valid");
    }

    if (Objects.isNull(
        caseTypeRepository.findByNameAndIsSubtypeAndOrgAndIsActiveTrue(subType, true, org))) {
      throw new BadRequestException("SubType is not valid");
    }
  }
}
