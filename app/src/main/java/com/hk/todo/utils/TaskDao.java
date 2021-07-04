package com.hk.todo.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.hk.todo.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Query("UPDATE task set title =:title, detail =:detail, status=:status WHERE id =:id")
    void updateTask(int id, String title, String detail, String status);

    @Delete
    void deleteTask(Task task);

    @Query("select * From task")
    LiveData<List<Task>> getAllTask();

    @Query("delete from task")
    void deleteAll();
}
