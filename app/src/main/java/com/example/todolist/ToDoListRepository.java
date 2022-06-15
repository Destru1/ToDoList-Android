package com.example.todolist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todolist.model.Task;
import com.example.todolist.utility.TaskRoomDatabase;

import java.util.List;

public class ToDoListRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTasks;
    private final LiveData<List<Task>> orderByDeadline;
    private final LiveData<List<Task>> orderByPriority;

    public ToDoListRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTasks = taskDao.getTasks();
        this.orderByDeadline = taskDao.orderByDeadline();
        this.orderByPriority = taskDao.orderByPriority();
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public LiveData<List<Task>> getOrderByDeadline(){ return orderByDeadline ;}
    public LiveData<List<Task>> getOrderByPriority(){ return orderByPriority ;}

    public void insert (Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.insertTask(task));
    }
    public LiveData<Task> get(long id){return taskDao.get(id);}

    public void update(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.update(task));
    }

    public void delete(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute(()-> taskDao.delete(task));
    }

}
