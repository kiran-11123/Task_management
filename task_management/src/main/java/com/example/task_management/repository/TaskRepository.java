package com.example.task_management.repository;
import com.example.task_management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task , Long>{


}
