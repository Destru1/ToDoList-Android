package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.todolist.adapter.OnToDoListClickListener;
import com.example.todolist.adapter.RecyclerViewAdapter;
import com.example.todolist.model.Priority;
import com.example.todolist.model.SharedViewModel;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnToDoListClickListener {

    private TaskViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        viewModel.getAllTasks().observe(this, tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks,this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        FloatingActionButton fab =findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showBottomSheetDialog();



               // Task task = new Task("Todo", Priority.HIGH, Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),false);
                //TaskViewModel.insert(task);
                //Snackbar.make(v,"Test",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }

            public void showBottomSheetDialog() {
                bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu_main,menu);
    return  true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.orderByDeadline) {
            viewModel.orderByDeadline.observe(this, tasks -> {
                recyclerViewAdapter = new RecyclerViewAdapter(tasks,this);
                recyclerView.setAdapter(recyclerViewAdapter);
            });
            return true;
        }
        else if (id == R.id.orderByPriority) {
            viewModel.orderByPriority.observe(this, tasks -> {
                recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
                recyclerView.setAdapter(recyclerViewAdapter);
            });
            return true;
        }
        else if (id == R.id.creationDate){
            viewModel.getAllTasks().observe(this, tasks -> {
                recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
                recyclerView.setAdapter(recyclerViewAdapter);
            });
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdited(true);
        bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());

    }



    @Override
    public void onToDoRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}