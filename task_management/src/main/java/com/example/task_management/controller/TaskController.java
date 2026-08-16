package com.example.task_management.controller;
import com.example.task_management.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.task_management.entity.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RequestMapping("/api/tasks")
@RestController
public class TaskController {

    private final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService service;

    public TaskController(TaskService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
         logger.info("Get All Tasks Controller is executing");
        return ResponseEntity.status(200).body(service.getAllTasks());
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
         logger.info("Create Task Controller is executing");
         return ResponseEntity.ok(service.createTask(task));
    }
}
