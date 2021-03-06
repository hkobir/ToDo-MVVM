package com.hk.todo.utils;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hk.todo.models.Task;

import java.util.List;

public class Repository {
    public TaskDao taskDao;
    LiveData<List<Task>> tasks;

    Repository(Application application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        taskDao = db.taskDao();
        tasks = taskDao.getAllTask();
    }

    LiveData<List<Task>> getAllTask() {
        return tasks;
    }

    void insertTask(final Task task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(task);
            }
        });
    }

    void updateTask(final int id, final String title, final String detail,final String status) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(id, title, detail, status);
            }
        });
    }

    void deleteTask(final Task task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }
}
