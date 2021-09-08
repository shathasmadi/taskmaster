package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Button addTask= (Button) findViewById(R.id.but);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTask = new Intent(MainActivity.this, AddTask.class);
                startActivity(addTask);
            }
        });

        Button allTask= (Button) findViewById(R.id.allTask);
        allTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allTask = new Intent(MainActivity.this, AllTask.class);
                startActivity(allTask);
            }
        });
//
//        Button shoppingButton = findViewById(R.id.shoppingButton);
//        shoppingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goShopping =new Intent(MainActivity.this,TaskDetails.class);
//                String title=shoppingButton.getText().toString();
//                goShopping.putExtra("title",title);
//                startActivity(goShopping);
//            }
//        });
//
//        Button readingButton = findViewById(R.id.readingButton);
//       readingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goReading =new Intent(MainActivity.this,TaskDetails.class);
//                String title=readingButton.getText().toString();
//                goReading.putExtra("title",title);
//                startActivity(goReading);
//            }
//        });
//
//        Button codingButton = findViewById(R.id.codingButton);
//        codingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goCoding =new Intent(MainActivity.this,TaskDetails.class);
//                String title=codingButton.getText().toString();
//                goCoding.putExtra("title",title);
//                startActivity(goCoding);
//            }
//        });

        Button settingButton = findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSetting =new Intent(MainActivity.this,Setting.class);
                startActivity(goSetting);
            }
        });

//        List<Task> tasks = new ArrayList<>();
//
//        tasks.add(new Task("Reading","Reading Body","new"));
//        tasks.add(new Task("Shopping","Shopping Body","new"));
//        tasks.add(new Task("Coding","Coding Body","new"));

//        AppDatabase database =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "shatha").allowMainThreadQueries().build();
//        TaskDao taskDao = database.taskDao();
//
//        List<Task> tasks= taskDao.taskData();

        RecyclerView taskDataRecyclerView = findViewById(R.id.recycle);

        List<TaskMaster> tasks= new ArrayList<>();

        Handler handler =new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                taskDataRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
        Amplify.API.query(
                ModelQuery.list(TaskMaster.class),
                response -> {
                    for (TaskMaster todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getTitle());

                        tasks.add(todo);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );


        taskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskDataRecyclerView.setAdapter(new AdaptorTask(tasks));
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = share.getString("username","User");
        TextView text = findViewById(R.id.view);
        text.setText(name);
    }
}