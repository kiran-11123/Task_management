package com.example.task_management.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequest {
    
    @NotBlank(message =   "Title is Required")
    @Size(min=3 , max=100 , message ="Title must been between 3 and 100 characters")
    private String title;

    @NotBlank(message =  "Description is Required")
    @Size(max  = 500 , message = "Description cannot exceed 500 characters")
    private String description;
    private boolean completed;
    public TaskRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title; 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
