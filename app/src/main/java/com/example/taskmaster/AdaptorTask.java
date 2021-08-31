package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptorTask extends RecyclerView.Adapter<AdaptorTask.TaskViewHolder> {
   List<Task> taskData=new ArrayList<>();

    public AdaptorTask(List<Task> taskData) {
        this.taskData = taskData;
    }

    @NonNull

    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks,parent,false);
        return new TaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  AdaptorTask.TaskViewHolder holder, int position) {
        holder.task = taskData.get(position);
        Button fragment= holder.itemView.findViewById(R.id.fragmentButton);
        fragment.setText(holder.task.title);


    }

    @Override
    public int getItemCount() {
        return taskData.size();
    }

    public static  class TaskViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        public Task task;

        public TaskViewHolder(@NonNull  View itemView) {
            super(itemView);
           this.itemView=itemView;

        }
    }


}
