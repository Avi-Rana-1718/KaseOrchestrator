package com.avirana.repository;

import com.avirana.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<CaseEntity, Integer> {
  CaseEntity findByTypeAndSubTypeAndCustomerEmailAndSubjectAndOrg(
      String caseType, String subType, String email, String subject, String org);

  CaseEntity findByTypeAndSubTypeAndCustomerMobileAndSubjectAndOrg(
      String caseType, String subType, String mobileNumber, String subject, String org);
}
