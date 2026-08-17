package com.example.task_management.controller;
import com.example.task_management.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_management.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Task>>> getTasks(){
         logger.info("Get All Tasks Controller is executing");
         List<Task> tasks = service.getAllTasks();

       ApiResponse<List<Task>> response = new ApiResponse<>(200 , "Tasks Fetched Successfully" , tasks);
       return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest request){
       logger.info("Creating task with title: {}", request.getTitle());      
       TaskResponse task = service.createTask(request);

       ApiResponse<TaskResponse> response = new ApiResponse<>(200 , "Task Created Successfully " , task);
       return ResponseEntity.status(201).body(response);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id){
        logger.info("Updating the task with id : {}" ,  id);

        Task task = service.updateTask(id);
        ApiResponse<Task> response = new ApiResponse<>(200 ,  "Task Updated  successfully",
                    task);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long id){
          logger.info("Fetching task with id : {}" , id);
          Task task = service.getTaskById(id);
          ApiResponse<Task> response = new ApiResponse<>(200 , "Task fetched successfully" , task);

          return ResponseEntity.ok(response);
    }
   @DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<String>> deleteTaskById(
        @PathVariable Long id) {

    logger.info("Deleting task with id: {}", id);

    service.deleteTaskById(id);

    ApiResponse<String> response =
            new ApiResponse<>(
                    200,
                    "Task deleted successfully",
                    null
            );

    return ResponseEntity.ok(response);
    }


}
