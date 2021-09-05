package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button save = findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedData = PreferenceManager.getDefaultSharedPreferences(Setting.this);
                SharedPreferences.Editor sharedEditor = sharedData.edit();
                EditText userName = findViewById(R.id.editText);
                String name = userName.getText().toString();
                sharedEditor.putString("username",name);
                sharedEditor.apply();
                Intent intent= new Intent(Setting.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}