package com.example.task_management.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.example.task_management.entity.User;

@Entity
@Table(name ="tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(){}

  public Task(String title , String description , boolean completed){
     this.title = title;
     this.description = description;
     this.completed = completed;
  }

  public Long getId(){
      return id;
  }
  public void setId(Long id){
    this.id = id;
  }

  public String getTitle(){
    return title;
  }
  public void setTitle(String title){
    this.title = title;
  }
  public String getDescription(){
      return description;
  }
  public void setDescription(String description){
    this.description = description;
  }
  public boolean getCompleted(){
    return completed;
  }
  public void setCompleted(boolean completed){
    this.completed= completed;
  }



}
