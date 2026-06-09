package com.avirana.controller;

import com.avirana.dto.TaskCreationRequest;
import com.avirana.dto.TaskDto;
import com.avirana.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping()
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks());
  }

  @PostMapping()
  public ResponseEntity<String> createTask(@RequestBody TaskCreationRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
  }
}
