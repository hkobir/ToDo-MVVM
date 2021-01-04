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

    public void insertSemester(Task task) {
        taskRepository.insertTask(task);
    }

    public void deleteSemester(Task task) {
        taskRepository.deleteSemester(task);
    }

    public void updateSemester(int id, String title, String detail) {
        taskRepository.updateTask(id, title, detail);
    }
}
