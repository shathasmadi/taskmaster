package com.example.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taskTable")
public class Task {
    public String title ;
    public String body;
    public String state;

    @PrimaryKey(autoGenerate = true)
    public int id;


    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }
}
