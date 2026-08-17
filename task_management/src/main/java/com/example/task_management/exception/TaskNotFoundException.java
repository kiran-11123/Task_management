package com.example.task_management.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
         super(message);
    }
}
