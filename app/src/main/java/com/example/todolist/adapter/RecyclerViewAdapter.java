package com.example.todolist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.Task;
import com.example.todolist.utility.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    private final OnToDoListClickListener toDoListClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnToDoListClickListener toDoListClickListener) {
        this.taskList = taskList;
        this.toDoListClickListener = toDoListClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Task task = taskList.get(position);
    String fromatted = Utils.formatDate(task.getDeadline());
    holder.task.setText(task.getTask());
    holder.toDoChip.setText(fromatted);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip toDoChip;
        OnToDoListClickListener onToDoListClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            task = itemView.findViewById(R.id.todo_row_todo);
            toDoChip = itemView.findViewById(R.id.todo_row_chip);
            this.onToDoListClickListener = toDoListClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Task currentTask = taskList.get(getAdapterPosition());
            if (id == R.id.todo_row_layout){
                onToDoListClickListener.onTodoClick(currentTask);
            }
            else if (id == R.id.todo_radio_button){
                onToDoListClickListener.onToDoRadioButtonClick(currentTask);
            }
        }
    }
}
