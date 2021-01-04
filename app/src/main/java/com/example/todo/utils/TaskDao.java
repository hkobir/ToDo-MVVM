package com.example.todo.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;

import com.example.todo.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Query("UPDATE task set title =:title, detail =:detail WHERE id =:id")
    void updateTask(int id, String title,  String detail);

    @Delete
    void deleteTask(Task task);

    @Query("select * From task order by id")
    LiveData<List<Task>> getAllTask();

    @Query("delete from task")
    void deleteAll();
}
