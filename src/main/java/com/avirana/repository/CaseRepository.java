package com.avirana.repository;

import com.avirana.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<CaseEntity, Integer> {
  CaseEntity findOneByCaseNumberAndOrgAndIsActiveTrue(String caseNumber, String org);
}
