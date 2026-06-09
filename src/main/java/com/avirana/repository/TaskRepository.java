package com.avirana.repository;

import com.avirana.dto.TaskDto;
import com.avirana.entity.TaskEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

  List<TaskDto> findAllByIsActiveTrue();

  TaskEntity findByIdAndIsActiveTrue(Integer taskId);
}
