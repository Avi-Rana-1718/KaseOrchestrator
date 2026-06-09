package com.avirana.service;

import com.avirana.dto.CaseCreationRequest;
import com.avirana.dto.CaseTypeUpsertRequest;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.CaseEntity;
import com.avirana.entity.CaseTypeEntity;
import com.avirana.enums.CaseStatusEnum;
import com.avirana.repository.CaseRepository;
import com.avirana.repository.CaseTypeRepository;
import com.avirana.util.CaseUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CaseService {

  private final CaseTypeRepository caseTypeRepository;
  private final CaseRepository caseRepository;

  private final CaseUtil caseUtil;

  @Transactional(readOnly = true)
  public List<String> getAllCaseType(XUserDetails userDetails, Boolean subType) {
    List<CaseTypeEntity> typeEntityList =
        caseTypeRepository.findAllByOrgAndIsSubtypeAndIsActiveTrue(userDetails.getOrg(), subType);
    List<String> typeList = new ArrayList<>();

    typeEntityList.forEach(
        e -> {
          typeList.add(e.getName());
        });

    return typeList;
  }

  @Transactional
  public String upsertCaseType(CaseTypeUpsertRequest request, XUserDetails userDetails) {
    CaseTypeEntity typeEntity =
        caseTypeRepository.findByOrgAndNameAndIsSubtype(
            userDetails.getOrg(), request.getName(), request.getIsSubtype());

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

  @Transactional(readOnly = true)
  public CaseEntity getCaseDetails(String caseNumber, XUserDetails userDetails)
      throws BadRequestException {
    CaseEntity caseEntity =
        caseRepository.findOneByCaseNumberAndOrgAndIsActiveTrue(caseNumber, userDetails.getOrg());

    if (Objects.isNull(caseEntity))
      throw new BadRequestException("No case with mentioned id exists.");

    return caseEntity;
  }

  @Transactional
  public String createCase(CaseCreationRequest request, XUserDetails userDetails)
      throws BadRequestException {

    String org = userDetails.getOrg();
    String caseType = request.getCaseType();
    String subject = request.getSubject();
    String subType = request.getSubType();
    String caseNumber =
        Objects.nonNull(request.getCaseNumber()) ? request.getCaseNumber() : generateCaseNumber();

    caseUtil.validateTypeAndSubtype(caseType, subType, org);

    CaseEntity caseEntity = new CaseEntity();
    caseEntity.setOrg(org);
    caseEntity.setStatus(CaseStatusEnum.NEW);
    caseEntity.setSubject(subject);
    caseEntity.setType(caseType);
    caseEntity.setCaseNumber(caseNumber);
    caseEntity.setSubType(subType);

    caseEntity = caseRepository.save(caseEntity);

    return "Created case with number: " + caseEntity.getCaseNumber();
  }

  private String generateCaseNumber() {
    return "KS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
  }
}
