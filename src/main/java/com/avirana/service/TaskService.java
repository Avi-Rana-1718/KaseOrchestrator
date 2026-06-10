package com.avirana.service;

import com.avirana.constants.KafkaTopics;
import com.avirana.dto.TaskCreationRequest;
import com.avirana.dto.TaskDto;
import com.avirana.entity.CaseEntity;
import com.avirana.entity.TaskEntity;
import com.avirana.enums.CaseStatusEnum;
import com.avirana.messaging.KafkaProducer;
import com.avirana.messaging.events.TaskEvent;
import com.avirana.repository.CaseRepository;
import com.avirana.repository.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final CaseRepository caseRepository;
  private final KafkaProducer kafkaProducer;

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

  @Transactional
  public String completeTask(TaskEvent taskEvent) {
    CaseEntity caseEntity = caseRepository.findById(taskEvent.getCaseId()).get();

    if (caseEntity.getStatus() != CaseStatusEnum.ONHOLD) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Case is not waiting for external intervention");
    }

    caseEntity.setStatus(CaseStatusEnum.ACTIVE);
    caseRepository.save(caseEntity);

    kafkaProducer.send(KafkaTopics.TASK_COMPLETED, taskEvent);

    return "Completed event";
  }
}
