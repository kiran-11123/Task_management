package com.example.task_management.service;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.entity.Task;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TaskService {
     
    private final TaskRepository repository;

    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public List<Task> getAllTasks(){
        logger.info("Fetching all Tasks");
          List<Task> tasks =  repository.findAll();
          logger.info("All Tasks Fetched successfully : {}" , tasks.size());
          return tasks;
    }
    public Task createTask(Task task){
         logger.info("Saving Task  {}" , task.getTitle());
         return repository.save(task);
    }

}
