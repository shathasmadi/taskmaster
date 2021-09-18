package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private static PinpointManager pinpointManager;

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", String.valueOf(userStateDetails.getUserState()));
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Amplify.Auth.signOut(
                        () -> {
                            Log.i("AuthQuickstart", "Signed out successfully");
                            Amplify.Auth.signInWithWebUI(
                                    MainActivity.this,
                                    result -> Log.i("AuthQuickStart", result.toString()),
                                    error -> Log.e("AuthQuickStart", error.toString())
                            );
                        },
                        error -> Log.e("AuthQuickstart", error.toString())
                );
            }
        });

        Amplify.Auth.signInWithWebUI(
                this,
                result -> Log.i("AuthQuickStart", result.toString()),
                error -> Log.e("AuthQuickStart", error.toString())
        );





//        Amplify.Auth.fetchAuthSession(
//                result -> Log.i("AmplifyQuickstart", result.toString()),
//                error -> Log.e("AmplifyQuickstart", error.toString())
//        );

//
//       Team team = Team.builder()
//                .name("Hanaa")
//                .build();
//
//        Amplify.API.mutate(
//                ModelMutation.create(team),
//                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
//                error -> Log.e("MyAmplifyApp", "Create failed", error));
//
//


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
        List<Team> teams=new ArrayList<>();

        Handler handler =new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                taskDataRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });


        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = share.getString("teamName","User");


        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        Log.i("MyAmplifyApp", team.getName());

                        teams.add(team);
                    }

                    for (int i = 0; i < teams.size(); i++) {
                        if (teams.get(i).getName().equals(name)){
                            tasks.addAll(teams.get(i).getTasks());
                        }
                    }

                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );


        taskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskDataRecyclerView.setAdapter(new AdaptorTask(tasks));
    }

    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences share = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = share.getString("username","User");
        TextView text = findViewById(R.id.view);
        text.setText(com.amazonaws.mobile.client.AWSMobileClient.getInstance().getUsername());
    }
}