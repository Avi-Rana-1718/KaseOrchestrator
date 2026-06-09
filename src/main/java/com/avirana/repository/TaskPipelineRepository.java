package com.avirana.repository;

import com.avirana.entity.TaskPipelineEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPipelineRepository extends JpaRepository<TaskPipelineEntity, Integer> {
  List<TaskPipelineEntity> findAllByOrgAndIsActiveTrue(String org);

  TaskPipelineEntity findOneByOrgAndTypeAndSubTypeAndIsActiveTrue(
      String org, String caseType, String subType);
}
