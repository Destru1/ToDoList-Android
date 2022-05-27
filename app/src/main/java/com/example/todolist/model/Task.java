package com.example.todolist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.reflect.Constructor;
import java.util.Date;

@Entity(tableName = "task_table")
public class Task {
    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    public String task;

    public Priority priority;

    public Date deadline;

    @ColumnInfo(name = "created_at")
    public Date createdAt;

    @ColumnInfo(name = "is_done")
    public boolean isDone;


   public Task(String task, Priority priority, Date deadline, Date createdAt, boolean isDone){
       this.task = task;
       this.priority = priority;
       this.deadline = deadline;
       this.createdAt = createdAt;
       this.isDone = isDone;
   }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", deadline=" + deadline +
                ", createdAt=" + createdAt +
                ", isDone=" + isDone +
                '}';
    }
}
