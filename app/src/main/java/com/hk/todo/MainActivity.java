package com.hk.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.hk.todo.adapters.TaskAdapter;

import com.hk.todo.databinding.ActivityMainBinding;
import com.hk.todo.models.Task;
import com.hk.todo.utils.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    List<Task> taskList;
    private TaskAdapter taskAdapter;
    TaskViewModel viewModel;
    private AlertDialog addTaskDialog;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //initialize

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskList = new ArrayList<>();
        binding.taskListRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //set Header text
        binding.dateTV.setText(dateFormat.format(new Date()));
        binding.greetingTV.setText(getGreeting());

        binding.addTaskFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        populateData();
    }


    private void populateData() {
        viewModel.getAllTask().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskList = tasks;

                binding.emptyTV.setVisibility(View.GONE);
                taskAdapter = new TaskAdapter(taskList, MainActivity.this);
                binding.taskListRV.setAdapter(taskAdapter);


                Log.d("taskData", tasks.toString());
                //empty item
                if (taskList.size() <= 0) {
                    binding.emptyTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addTask() {
        addTaskDialog = new AlertDialog.Builder(MainActivity.this).create();
        addTaskDialog.setCancelable(false);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_task_dialogue, null);
        final EditText titleET = view.findViewById(R.id.titleET);
        final EditText detailET = view.findViewById(R.id.detailET);
        Button saveBtn = view.findViewById(R.id.saveButton);
        Button cancelBtn = view.findViewById(R.id.cancelButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleET.getText().toString().trim().equals("")) {
                    titleET.setError("Task title");
                    titleET.requestFocus();
                    return;
                }
                if (detailET.getText().toString().trim().equals("")) {
                    detailET.setError("Task detail");
                    detailET.requestFocus();
                    return;
                }
                saveTaskData(
                        titleET.getText().toString(),
                        detailET.getText().toString()
                );
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog.dismiss();
            }
        });
        addTaskDialog.setView(view);
        addTaskDialog.show();
    }

    private void saveTaskData(String title, String detail) {
        String cDate = dateFormat.format(new Date());
        Task task = new Task(title, detail, cDate, "undone");
        viewModel.insertTask(task);
        taskAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        addTaskDialog.dismiss();
    }


    private String getGreeting() {
        String say = "";
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 12 && hour < 17) {
            say = "Good Afternoon!";
        } else if (hour >= 17 && hour < 21) {
            say = "Good Evening!";
        } else if (hour >= 21 && hour < 24) {
            say = "Good Night!";
        } else {
            say = "Good Morning!";
        }
        return say;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}