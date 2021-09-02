package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);


        TextView taskTitle = findViewById(R.id.textViewr);
        TextView taskDescription = findViewById(R.id.textView9);
        TextView taskState = findViewById(R.id.textView5);


        Intent intent= getIntent();
        taskTitle.setText(intent.getExtras().getString("Title"));//set the data use setText
        taskDescription.setText(intent.getExtras().getString("Description"));//set the data use setText
        taskState.setText(intent.getExtras().getString("State"));//set the data use setText


//        String title = intent.getExtras().getString("title");
//        TextView textView= findViewById(R.id.textViewr);
//        textView.setText(title);



        Button homeButton = findViewById(R.id.button2);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome =new Intent(TaskDetails.this,MainActivity.class);
                startActivity(goHome);
            }
        });

    }
}