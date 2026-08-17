package com.example.task_management.controller;
import com.example.task_management.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RequestMapping("/api/tasks")
@RestController
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService service;

    public TaskController(TaskService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
         logger.info("Get All Tasks Controller is executing");
        return ResponseEntity.ok(service.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request){
       logger.info("Creating task with title: {}", request.getTitle());      
       return ResponseEntity.ok(service.createTask(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id){
        logger.info("Updating the task with id : {}" ,  id);
        return ResponseEntity.ok(service.updateTask(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
          logger.info("Fetching task with id : {}" , id);
          return ResponseEntity.ok(service.getTaskById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
         logger.info("Deleting the Task with id : {} ", id);
         service.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }


}
