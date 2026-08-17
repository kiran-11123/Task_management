package com.example.task_management.repository;
import com.example.task_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserRepository extends JpaRepository<User  , Long> {
     
    Optional<User> findByEmail(String email);
}
