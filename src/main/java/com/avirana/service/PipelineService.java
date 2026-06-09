package com.avirana.service;

import com.avirana.dto.AssociatedTaskDto;
import com.avirana.dto.PipelineCreationRequest;
import com.avirana.dto.PipelineDetailsDto;
import com.avirana.dto.XUserDetails;
import com.avirana.entity.TaskEntity;
import com.avirana.entity.TaskPipelineEntity;
import com.avirana.entity.TaskPipelineStepEntity;
import com.avirana.repository.TaskPipelineRepository;
import com.avirana.repository.TaskPipelineStepRepository;
import com.avirana.repository.TaskRepository;
import com.avirana.util.CaseUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PipelineService {

  private final TaskPipelineRepository taskPipelineRepository;
  private final TaskPipelineStepRepository taskPipelineStepRepository;
  private final TaskRepository taskRepository;
  private final CaseUtil caseUtil;

  @Transactional
  public String createPipeline(
      PipelineCreationRequest pipelineCreationRequest, XUserDetails userDetails)
      throws BadRequestException {

    String name = pipelineCreationRequest.getName();
    String caseType = pipelineCreationRequest.getCaseType();
    String subType = pipelineCreationRequest.getSubType();
    List<AssociatedTaskDto> tasks = pipelineCreationRequest.getTasks();

    System.out.println("case type is" + caseType);
    caseUtil.validateTypeAndSubtype(caseType, subType, userDetails.getOrg());

    TaskPipelineEntity taskPipelineEntity = new TaskPipelineEntity();
    taskPipelineEntity.setName(name);
    taskPipelineEntity.setType(caseType);
    taskPipelineEntity.setSubType(subType);
    taskPipelineEntity.setOrg(userDetails.getOrg());

    taskPipelineRepository.save(taskPipelineEntity);

    List<TaskPipelineStepEntity> taskPipelineStepEntities = new ArrayList<>();
    tasks.forEach(
        task -> {
          Integer pipelineId = taskPipelineEntity.getId();

          validateTasks(List.of(task.getTaskId()));
          validateTasks(task.getDependsOn());

          TaskPipelineStepEntity taskPipelineStepEntity = new TaskPipelineStepEntity();
          taskPipelineStepEntity.setPipelineId(pipelineId);
          taskPipelineStepEntity.setTaskId(task.getTaskId());
          taskPipelineStepEntity.setSequenceNumber(task.getStep());
          taskPipelineStepEntity.setDependsOn(task.getDependsOn());

          taskPipelineStepEntities.add(taskPipelineStepEntity);
        });

    taskPipelineStepRepository.saveAll(taskPipelineStepEntities);

    return "Created pipeline";
  }

  @Transactional(readOnly = true)
  public List<TaskPipelineEntity> getAllPipelines(XUserDetails userDetails) {
    return taskPipelineRepository.findAllByOrgAndIsActiveTrue(userDetails.getOrg());
  }

  private void validateTasks(List<Integer> taskIds) {
    taskIds.forEach(
        taskId -> {
          if (Objects.isNull(taskRepository.findByIdAndIsActiveTrue(taskId))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid taskId");
          }
        });
  }

  @Transactional(readOnly = true)
  public PipelineDetailsDto getPipelineDetails(Integer pipelineId) {
    TaskPipelineEntity pipelineEntity = taskPipelineRepository.findById(pipelineId).get();
    List<TaskPipelineStepEntity> steps =
        taskPipelineStepRepository.findAllByPipelineIdOrderBySequenceNumberAsc(pipelineId);

    if (Objects.isNull(pipelineEntity))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid pipelineId");

    List<String> tasks = new ArrayList<>();

    steps.forEach(
        step -> {
          TaskEntity taskEntity = taskRepository.findById(step.getTaskId()).get();
          tasks.add(taskEntity.getName());
        });

    PipelineDetailsDto pipelineDetailsDto = new PipelineDetailsDto();
    pipelineDetailsDto.setPipelineName(pipelineEntity.getName());
    pipelineDetailsDto.setCaseType(pipelineEntity.getType());
    pipelineDetailsDto.setSubType(pipelineEntity.getSubType());
    pipelineDetailsDto.setTasks(tasks);

    return pipelineDetailsDto;
  }
}
