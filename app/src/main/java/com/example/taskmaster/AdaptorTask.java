package com.example.taskmaster;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskMaster;

import java.util.ArrayList;
import java.util.List;

public class AdaptorTask extends RecyclerView.Adapter<AdaptorTask.TaskViewHolder> {
   List<TaskMaster> taskData=new ArrayList<>();

    public AdaptorTask(List<TaskMaster> taskData) {
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
        fragment.setText(holder.task.getTitle());


    }

    @Override
    public int getItemCount() {
        return taskData.size();
    }

    public static  class TaskViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        public TaskMaster task;


        public TaskViewHolder(@NonNull  View itemView) {
            super(itemView);
           this.itemView=itemView;

           itemView.findViewById(R.id.fragmentButton).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(v.getContext(),TaskDetails.class);
                   intent.putExtra("Title",task.getTitle());
                   intent.putExtra("Description",task.getBody());
                   intent.putExtra("State",task.getState());
                   intent.putExtra("picture",task.getImage());
                   intent.putExtra("Latitude",task.getLatitude());
                   intent.putExtra("Longitude",task.getLongitude());
                   v.getContext().startActivity(intent);
               }
           });

        }
    }


}
