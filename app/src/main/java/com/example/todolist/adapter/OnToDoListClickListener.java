package com.example.todolist.adapter;

import com.example.todolist.model.Task;

public interface OnToDoListClickListener {
    void onTodoClick(Task task);

    void onToDoRadioButtonClick(Task task);
}
