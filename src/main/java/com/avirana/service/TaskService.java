package com.avirana.service;

import com.avirana.dto.TaskCreationRequest;
import com.avirana.dto.TaskDto;
import com.avirana.entity.TaskEntity;
import com.avirana.repository.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  @Transactional(readOnly = true)
  public List<TaskDto> getAllTasks() {
    List<TaskDto> taskList = taskRepository.findAllByIsActiveTrue();

    return taskList;
  }

  @Transactional
  public String createTask(TaskCreationRequest taskCreationRequest) {
    String name = taskCreationRequest.getName();
    String identifier = taskCreationRequest.getIdentifier();

    TaskEntity taskEntity = new TaskEntity(name, identifier);
    taskRepository.save(taskEntity);

    return "Task successfully added";
  }
}
