package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);


        TextView taskTitle = findViewById(R.id.textViewr);
        TextView taskDescription = findViewById(R.id.textView9);
        TextView taskState = findViewById(R.id.textView5);
        TextView latAndLon = findViewById(R.id.textView13);

        Intent intent= getIntent();
        taskTitle.setText(intent.getExtras().getString("Title"));//set the data use setText
        taskDescription.setText(intent.getExtras().getString("Description"));//set the data use setText
        taskState.setText(intent.getExtras().getString("State"));//set the data use setText
        latAndLon.setText(intent.getExtras().getString("Latitude")+"   "+intent.getExtras().getString("Longitude")+"     ");

//        String title = intent.getExtras().getString("title");
//        TextView textView= findViewById(R.id.textViewr);
//        textView.setText(title);

        Amplify.Storage.downloadFile(
                intent.getExtras().getString("picture"),
                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
                result -> {
                    ImageView image = findViewById(R.id.imageView2);
                    image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile());},
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

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