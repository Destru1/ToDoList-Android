package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.todolist.model.Priority;
import com.example.todolist.model.SharedViewModel;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.security.Security;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTask;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioGroup;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date deadline;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        if (sharedViewModel.getSelectedItem().getValue() != null){
            Task task = sharedViewModel.getSelectedItem().getValue();

        }

        calendarButton.setOnClickListener(v -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        calendarView.setOnDateChangeListener(((view1, year, month, dayOfMonth) -> {
        calendar.clear();
        calendar.set(year,month,dayOfMonth);
        deadline = calendar.getTime();
        }) );

        saveButton.setOnClickListener(v -> {
            String task = enterTask.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && deadline != null){
                Task myTask = new Task(task, Priority.HIGH, deadline, Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(myTask);
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