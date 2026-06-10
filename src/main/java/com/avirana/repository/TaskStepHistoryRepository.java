package com.avirana.repository;

import com.avirana.entity.TaskStepHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStepHistoryRepository extends JpaRepository<TaskStepHistoryEntity, Integer> {}
