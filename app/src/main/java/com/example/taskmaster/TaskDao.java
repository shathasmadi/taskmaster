package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM taskTable")
    List<Task> taskData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(Task... task);
}
