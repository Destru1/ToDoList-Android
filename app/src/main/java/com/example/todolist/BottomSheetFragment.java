package com.example.todolist;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.model.Priority;
import com.example.todolist.model.SharedViewModel;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskViewModel;
import com.example.todolist.utility.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;


public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTask;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date deadline;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdited;
    private Priority priority;


    public BottomSheetFragment(){

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        calendarView = view.findViewById(R.id.calendar_view);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTask = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);


        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null){
           isEdited = sharedViewModel.getIsEdited();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTask.setText(task.getTask());
            Log.d("MY", "onViewCreated:" + task.getTask());

        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);




        calendarButton.setOnClickListener(v -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideKeyboard(v);
        });

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
        calendar.clear();
        calendar.set(year,month,dayOfMonth);
        deadline = calendar.getTime();
        } );

        priorityButton.setOnClickListener(view3 -> {
            Utils.hideKeyboard(view3);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

            priorityRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE){
                    selectedButtonId = checkedId;
                    selectedRadioButton = view.findViewById(selectedButtonId);
                    if (selectedRadioButton.getId() == R.id.radioButton_high){
                        priority = Priority.HIGH;
                    }
                    else if (selectedRadioButton.getId() == R.id.radioButton_med){
                        priority = Priority.MEDIUM;
                    }
                    else if(selectedRadioButton.getId() ==R.id.radioButton_low){
                        priority = Priority.LOW;
                    }
                    else {
                        priority = Priority.NULL;
                    }
                }
                else {
                    priority = Priority.NULL;
                }
            });

        });


        saveButton.setOnClickListener(v -> {
            String task = enterTask.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && deadline != null){
                Task myTask = new Task(task, priority, deadline, Calendar.getInstance().getTime(), false);
                if (isEdited){
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(task);
                    updateTask.setCreatedAt(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDeadline(deadline);
                    TaskViewModel.update(updateTask);
                    Toast toast = Toast.makeText(getContext(),"Task updated",Toast.LENGTH_SHORT);
                    toast.show();
                    sharedViewModel.setIsEdited(false);
                }else
                {
                    TaskViewModel.insert(myTask);
                    Toast toast = Toast.makeText(getContext(),"Task created",Toast.LENGTH_SHORT);
                    toast.show();
                }
                enterTask.setText("");
                priority = null;
                if (this.isVisible()){
                    this.dismiss();
                }
                else {
                    Snackbar.make(saveButton, "Empty",Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.today_chip){
            calendar.add(Calendar.DAY_OF_YEAR,0);
            deadline = calendar.getTime();
            Log.d("TIME","onClick"+ deadline.toString());
        }
        else if (id == R.id.tomorrow_chip){
            calendar.add(Calendar.DAY_OF_YEAR,1);
            deadline = calendar.getTime();
            Log.d("TIME","onClick"+ deadline.toString());
        }
        else if (id == R.id.next_week_chip){
            calendar.add(Calendar.DAY_OF_YEAR,7);
            deadline =calendar.getTime();
            Log.d("TIME","onClick"+ deadline.toString());
        }
    }
}