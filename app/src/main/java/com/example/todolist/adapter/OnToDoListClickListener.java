package com.example.todolist.adapter;

import com.example.todolist.model.Task;

public interface OnToDoListClickListener {
    void onTodoClick(int adapterPosition, Task task);
}
