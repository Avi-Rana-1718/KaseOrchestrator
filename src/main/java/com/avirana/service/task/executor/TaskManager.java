package com.avirana.service.task.executor;

import com.avirana.constants.KafkaTopics;
import com.avirana.entity.CaseEntity;
import com.avirana.entity.TaskEntity;
import com.avirana.entity.TaskPipelineStepEntity;
import com.avirana.enums.CaseStatusEnum;
import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.KafkaProducer;
import com.avirana.messaging.events.TaskEvent;
import com.avirana.repository.CaseRepository;
import com.avirana.repository.TaskPipelineStepRepository;
import com.avirana.repository.TaskRepository;
import com.avirana.service.task.definition.TaskDefinition;
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
  private final TaskRepository taskRepository;
  private final TaskPipelineStepRepository taskPipelineStepRepository;
  private final CaseRepository caseRepository;
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
  public void increment(TaskEvent taskEvent) {
    Integer pipelineId = taskEvent.getPipelineId();
    Integer stepNumber = taskEvent.getStep();
    Integer caseId = taskEvent.getCaseId();

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
}
