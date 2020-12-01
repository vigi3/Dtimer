package com.proj.dtimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView addProject;
    Button goTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addProject = findViewById(R.id.addProject);
        addProject.setOnClickListener(this);
        goTimer = findViewById(R.id.goButton);
        goTimer.setOnClickListener(this);



    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addProject:
                TextEnterDialogFragment textDialog = new TextEnterDialogFragment();
                textDialog.show(getSupportFragmentManager(), "TextEnterDialogFragment");
                break;
            case R.id.goButton:
                Intent intent = new Intent(this, Timer.class);
                startActivity(intent);
                break;
        }



    }

    public void addNewProject(){


    }

}
