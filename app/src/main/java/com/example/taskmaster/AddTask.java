package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        List<Team> teams=new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team: response.getData()) {
                        Log.i("MyAmplifyApp", team.getName());

                        teams.add(team);
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );









//        AppDatabase database =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "shatha").allowMainThreadQueries().build();
//        TaskDao taskDao = database.taskDao();

        Button addTasks= (Button) findViewById(R.id.button);
        addTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_LONG).show();

                EditText taskTitle= findViewById(R.id.taskTitleView);
                EditText taskDescription= findViewById(R.id.taskDescriptionView);
                EditText taskState= findViewById(R.id.taskStateView);
//
//                 Task task = new Task(taskTitle.getText().toString(),taskDescription.getText().toString(),taskState.getText().toString());
//                 taskDao.insertAll(task);


                RadioButton hanaaButton = findViewById(R.id.radioButtonOne);
                RadioButton shathaButton = findViewById(R.id.radioButtonTwo);
                RadioButton smadiButton = findViewById(R.id.radioButtonThree);

                  String checkedButton = "";

                if(hanaaButton.isChecked()){
                    checkedButton = hanaaButton.getText().toString();
                }else if (shathaButton.isChecked()){
                    checkedButton = shathaButton.getText().toString();
                }else{
                    checkedButton = smadiButton.getText().toString();
                }


                Team teamOne = null;

                for(int i=0 ; i<teams.size() ; i++){
                    if(teams.get(i).getName().equals(checkedButton)){
                        teamOne = teams.get(i);
                    }
                }



                TaskMaster todo = TaskMaster.builder()
                        .title(taskTitle.getText().toString())
                        .body(taskDescription.getText().toString())
                        .state(taskState.getText().toString())
                        .team(teamOne)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error));

            }
        });


    }
}