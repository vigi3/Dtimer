package com.proj.dtimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextEnterDialogFragment.TextEnterDialogListener {

    TextView addProject;
    Button goTimer;
    TextInputEditText projectName;
    private DataBaseManager dbInst;
    private Projects projects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addProject = findViewById(R.id.addProject);
        addProject.setOnClickListener(this);
        goTimer = findViewById(R.id.goButton);
        goTimer.setOnClickListener(this);
        readProjects();



    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addProject:
                textEnterDialogFragment();
                break;
            case R.id.goButton:
                Intent intent = new Intent(this, Timer.class);
                startActivity(intent);
                break;
        }



    }

    public void textEnterDialogFragment() {
        // Create an instance of the dialog fragment and show it
        TextEnterDialogFragment textDialog = new TextEnterDialogFragment();
        textDialog.show(getSupportFragmentManager(), "TextEnterDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Positive Event is return by interface TextEnterDialogListener so logic can be done here
        projectName = dialog.getDialog().findViewById(R.id.textInput);
        if (TextUtils.isEmpty(projectName.getEditableText())) {
            // if dialogBox empty, restart dialogBox
            textEnterDialogFragment();
            TextInputLayout textInputLayout = dialog.getDialog().findViewById(R.id.textInputLayout);
            textInputLayout.setHint("Name missing !");
            Log.e("Dialog", "Project name is null/empty");

//            throw new IllegalArgumentException();
        }
        else {
            projects = new Projects();
            projects.setName(String.valueOf(projectName.getText()));
            dbInst = new DataBaseManager(this);
            dbInst.addProject(projects);
            dbInst.close();
            dialog.dismiss();
            readProjects();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }



    public void readProjects() {
        dbInst = new DataBaseManager(this);
        final List<Projects> projectsList = dbInst.showAllProjects();
        if (projectsList.isEmpty()) {
            Log.e("ReadingProject", "Project List is null/empty");
        }
        else {
            for (Projects myProjectList: projectsList) {

                ConstraintLayout constraintLayout = findViewById(R.id.activity_main);

                TextView projectTextView = new TextView(this);
                projectTextView.setText(myProjectList.getName());
                projectTextView.setId(R.id.projectTextViewId);
                projectTextView.setBackground(getDrawable(R.drawable.circle));
                projectTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                projectTextView.setGravity(Gravity.CENTER);
                projectTextView.setTextSize(20);
                projectTextView.setTextColor(getColor(R.color.colorOrange));

                constraintLayout.addView(projectTextView);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(projectTextView.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.RIGHT);
            }
        }
    }





}


