package com.example.task_management.repository;
import com.example.task_management.entity.Task;
import java.util.*;
import com.example.task_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task , Long>{

   List<Task> findByUser(User user);
}
