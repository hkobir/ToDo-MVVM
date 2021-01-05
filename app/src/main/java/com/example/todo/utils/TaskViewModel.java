package com.example.todo.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo.models.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private Repository taskRepository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new Repository(application);
        tasks = taskRepository.getAllTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return tasks;
    }

    public void insertTask(Task task) {
        taskRepository.insertTask(task);
    }

    public void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }

    public void updateTask(int id, String title, String detail) {
        taskRepository.updateTask(id, title, detail);
    }
}
