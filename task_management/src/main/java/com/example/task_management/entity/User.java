package com.example.task_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
     
      
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    
     private Long id;
     
     @Column(nullable = false)
     private String name;
      
     @Column(unique = true , nullable =  false)
     private String email;
     
     @Column(nullable =  false)
     private String password;
      
     @JsonIgnore
     @OneToMany(mappedBy = "user")
     private List<Task> tasks;

    

}
