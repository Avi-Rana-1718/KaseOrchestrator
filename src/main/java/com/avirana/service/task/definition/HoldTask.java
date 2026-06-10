package com.avirana.service.task.definition;

import com.avirana.entity.CaseEntity;
import com.avirana.enums.CaseStatusEnum;
import com.avirana.enums.TaskTypeEnum;
import com.avirana.messaging.events.TaskEvent;
import com.avirana.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HoldTask implements TaskDefinition {

  private final CaseRepository caseRepository;

  @Override
  public boolean execute(TaskEvent payload) {

    Integer caseId = payload.getCaseId();

    CaseEntity caseEntity = caseRepository.findById(caseId).get();

    caseEntity.setStatus(CaseStatusEnum.ONHOLD);
    caseRepository.save(caseEntity);

    return false;
  }

  @Override
  public TaskTypeEnum getType() {
    return TaskTypeEnum.HOLD;
  }
}
