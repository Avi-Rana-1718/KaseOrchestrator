package com.avirana.service.task.executor;

import com.avirana.constants.KafkaTopics;
import com.avirana.entity.CaseEntity;
import com.avirana.entity.TaskEntity;
import com.avirana.entity.TaskPipelineStepEntity;
import com.avirana.entity.TaskStepHistoryEntity;
import com.avirana.enums.CaseStatusEnum;
import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.KafkaProducer;
import com.avirana.messaging.events.TaskEvent;
import com.avirana.repository.CaseRepository;
import com.avirana.repository.TaskPipelineStepRepository;
import com.avirana.repository.TaskRepository;
import com.avirana.repository.TaskStepHistoryRepository;
import com.avirana.service.task.definition.TaskDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManager {
  private final List<TaskDefinition> taskList;
  private Map<TaskTypeEnum, TaskDefinition> taskMap = new HashMap<>();
  private final ObjectMapper objectMapper;

  private final CaseRepository caseRepository;
  private final TaskRepository taskRepository;
  private final TaskPipelineStepRepository taskPipelineStepRepository;
  private final TaskStepHistoryRepository taskStepHistoryRepository;

  private final KafkaProducer kafkaProducer;

  @PostConstruct
  public void init() {
    taskList.forEach(
        task -> {
          TaskTypeEnum type = task.getType();

          if (taskMap.containsKey(type)) throw new Error(type.toString() + " already handled!");

          taskMap.put(type, task);
        });
  }

  @Transactional()
  public void execute(TaskEvent payload) {
    TaskEntity taskEntity = taskRepository.findByIdAndIsActiveTrue(payload.getTaskId());
    TaskTypeEnum taskType = TaskTypeEnum.valueOf(taskEntity.getIdentifier());

    Integer stepNumber = payload.getStep();
    Integer caseId = payload.getCaseId();

    if (stepNumber == 0) {
      CaseEntity caseEntity = caseRepository.findById(caseId).get();
      caseEntity.setStatus(CaseStatusEnum.ACTIVE);
      caseRepository.save(caseEntity);
    }

    if (!taskMap.containsKey(taskType)) throw new Error(taskType + " not implemented!");

    log.info("Handled event: " + taskType);

    boolean shouldContinue = taskMap.get(taskType).execute(payload);

    if (shouldContinue) kafkaProducer.send(KafkaTopics.TASK_COMPLETED, payload);
  }

  @Transactional
  public void increment(TaskEvent taskEvent) throws JsonProcessingException {
    Integer pipelineId = taskEvent.getPipelineId();
    Integer stepNumber = taskEvent.getStep();
    Integer caseId = taskEvent.getCaseId();

    saveHistory(taskEvent);

    stepNumber++;

    TaskPipelineStepEntity nextStep =
        taskPipelineStepRepository.findOneByPipelineIdAndSequenceNumber(pipelineId, stepNumber);

    if (Objects.isNull(nextStep)) {
      CaseEntity caseEntity = caseRepository.findById(caseId).get();
      caseEntity.setStatus(CaseStatusEnum.CLOSED);
      caseRepository.save(caseEntity);

      log.info("Closing case as all tasks are completed: " + caseEntity.getCaseNumber());
      return;
    }

    taskEvent.setTaskId(nextStep.getTaskId());
    taskEvent.setStep(stepNumber);

    kafkaProducer.send(KafkaTopics.TASK_READY, taskEvent);
  }

  private void saveHistory(TaskEvent taskEvent) throws JsonProcessingException {
    TaskStepHistoryEntity taskStepHistoryEntity = new TaskStepHistoryEntity();
    taskStepHistoryEntity.setTaskId(taskEvent.getTaskId());
    taskStepHistoryEntity.setPipelineId(taskEvent.getPipelineId());
    taskStepHistoryEntity.setCaseId(taskEvent.getCaseId());
    taskStepHistoryEntity.setSequenceNumber(taskEvent.getStep());
    taskStepHistoryEntity.setRemarks(taskEvent.getRemarks());
    taskStepHistoryEntity.setPayload(objectMapper.writeValueAsString(taskEvent.getPayload()));

    taskStepHistoryRepository.save(taskStepHistoryEntity);
  }
}
