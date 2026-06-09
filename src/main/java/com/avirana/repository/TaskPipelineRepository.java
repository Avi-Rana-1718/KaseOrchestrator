package com.avirana.repository;

import com.avirana.entity.TaskPipelineEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPipelineRepository extends JpaRepository<TaskPipelineEntity, Integer> {
  List<TaskPipelineEntity> findAllByOrgAndIsActiveTrue(String org);

  Optional<TaskPipelineEntity> findById(Integer id);
}
