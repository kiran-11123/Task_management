package com.example.task_management.service;
import com.example.task_management.repository.TaskRepository;
import com.example.task_management.entity.Task;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
     
    private final TaskRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

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

    public Task getTaskById(Long id){
          logger.info("Fetching task with id : {}" , id);

          Optional<Task> task = repository.findById(id);

          if(task.isPresent()){
             return task.get();
          }
          throw new RuntimeException("Task not found with id : " + id);
    }
    public Task updateTask(Long id){
          logger.info("Updating the task with id : " + id); 

        Optional<Task> task = repository.findById(id);
        
        if(task.isPresent()){
              Task existingTask = task.get();
              existingTask.setCompleted(!existingTask.getCompleted());

              return repository.save(existingTask);
        }
         throw new RuntimeException("Task not found with id : " + id);
    }

    public void deleteTaskById(Long id) {

    logger.info("Deleting task with id: {}", id);

    Optional<Task> task = repository.findById(id);

    if (task.isPresent()) {

        repository.delete(task.get());

        logger.info("Task deleted successfully with id: {}", id);

        return;
    }

    throw new RuntimeException(
            "Task not found with id: " + id
    );
}

}
