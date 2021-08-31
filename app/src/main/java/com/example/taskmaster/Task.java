package com.example.taskmaster;

import androidx.constraintlayout.solver.state.State;

public class Task {
    public String title ;
    public String body;
    public String state;

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }
}
