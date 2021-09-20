package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    String picture="";

    FusedLocationProviderClient mFusedLocationClient;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();

        Intent intentRecieving = getIntent();
        if(intentRecieving.getType() !=null && intentRecieving.getType().equals("text/plain")){
            EditText taskDescription= findViewById(R.id.taskDescriptionView);
            taskDescription.setText(intentRecieving.getExtras().get(Intent.EXTRA_TEXT).toString());
        }



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


        Button uploadPicture =findViewById(R.id.button3);
        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFile();
            }
        });



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
                        .image(picture)
                        .team(teamOne)
                        .build();


                Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error));

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


       mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 99);

            boolean test =ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat
                            .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED;


            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("**************************LOCATION permission*******************************");
            System.out.println("**********check activity compact "+ test );
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            System.out.println("**************************LOCATION*******************************");
                            System.out.println(location.toString());

                            double longitude= location.getLongitude();
                            double latitude= location.getLatitude();
                            System.out.println("Latitude: " + latitude+" - "+ "Longitude: " +
                                    longitude);
                        }
                    }

                });

    }

    public void getFile(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent=Intent.createChooser(intent,"get file");
        startActivityForResult(intent,1997);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
            assert data != null;
            InputStream exampleInputStream = getContentResolver().openInputStream(data.getData());

            picture = data.getData().getPath().toString();


            Amplify.Storage.uploadInputStream(
                    data.getData().getPath().toString(),
                    exampleInputStream,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }  catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }
    }

}