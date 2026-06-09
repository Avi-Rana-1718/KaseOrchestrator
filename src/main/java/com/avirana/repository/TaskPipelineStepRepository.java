package com.avirana.repository;

import com.avirana.entity.TaskPipelineStepEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskPipelineStepRepository extends JpaRepository<TaskPipelineStepEntity, Integer> {
  List<TaskPipelineStepEntity> findAllByPipelineIdOrderBySequenceNumberAsc(Integer pipelineId);

  TaskPipelineStepEntity findOneByPipelineIdAndSequenceNumber(Integer pipelineId, Integer step);
}
