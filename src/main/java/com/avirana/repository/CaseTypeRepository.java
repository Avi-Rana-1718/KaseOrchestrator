package com.avirana.repository;

import com.avirana.entity.CaseTypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseTypeRepository extends JpaRepository<CaseTypeEntity, Integer> {
  List<CaseTypeEntity> findAllByOrgAndIsActiveTrueAndIsSubtype(String org, Boolean subtype);

  CaseTypeEntity findByNameAndOrgAndIsSubtype(String name, String org, Boolean isSubtype);

  CaseTypeEntity findByNameAndIsSubtypeAndOrgAndIsActiveTrue(
      String name, Boolean isSubType, String org);
}
