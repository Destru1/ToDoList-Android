package com.example.todolist.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.ToDoListRepository;

import java.util.Collections;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static ToDoListRepository repository;
    public final LiveData<List<Task>> allTasks;
    public final LiveData<List<Task>> orderByDeadline;
    public final LiveData<List<Task>> orderByPriority;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoListRepository(application);
        allTasks = repository.getAllTasks();
        orderByDeadline = repository.getOrderByDeadline();
        orderByPriority = repository.getOrderByPriority();
    }

    public LiveData<List<Task>> getAllTasks() {return allTasks; }
    public LiveData<List<Task>> getOrderByDeadline() {return orderByDeadline; }
    public LiveData<List<Task>> getOrderByPriority() {return orderByPriority; }
    public static void insert(Task task) {repository.insert(task); }
    public LiveData<Task> get (long id) {return repository.get(id); }
    public static void update(Task task) {repository.update(task); }
    public static void delete(Task task) {repository.delete(task); }





}
