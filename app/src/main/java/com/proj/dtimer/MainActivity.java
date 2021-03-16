package com.proj.dtimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextEnterDialogFragment.TextEnterDialogListener {

    TextView addProject;
    TextView textViewTopLeft;
    TextView textViewTopRight;
    TextView textViewBottomLeft;
    TextView textViewBottomRight;
    Button goTimer;
    TextInputEditText projectName;
    private DataBaseManager dbInst;
    private Projects projects;
    private int textViewCount;
    private int index = 0;
    int[] idProjectView = {0, 0, 0, 0, 0, 0, 0, 0};
    String[] nameProjectView = {"", "", "", "", "","","",""};
    TextView[] textViewArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        setContentView(R.layout.activity_main);
        addProject = findViewById(R.id.addProject);
        addProject.setOnClickListener(this);
//        goTimer = findViewById(R.id.goButton);
//        goTimer.setOnClickListener(this);

        textViewArray = new TextView[8];
        textViewCount = textViewArray.length;
        textViewArray[0] = findViewById(R.id.textViewTopLeft);
        textViewArray[1] = findViewById(R.id.textViewTopRight);
        textViewArray[2] = findViewById(R.id.textViewMiddleTop);
        textViewArray[3] = findViewById(R.id.textViewMiddleBottom);
        textViewArray[4] = findViewById(R.id.textViewBottomLeft);
        textViewArray[5] = findViewById(R.id.textViewbottomRight);
        textViewArray[6] = findViewById(R.id.textViewMiddleLeft);
        textViewArray[7] = findViewById(R.id.textViewMiddleRight);


        SetOnClickListenerOnProject(textViewArray);

        readProjects();


    }






    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addProject:
                textEnterDialogFragment();
                break;
//            case R.id.goButton:
//                Intent intent = new Intent(this, Timer.class);
//                startActivity(intent);
//                break;
            case R.id.textViewTopLeft:
                Intent intentTopLeft = new Intent(this, ScrollingTaskActivity.class);
                intentTopLeft.putExtra("EXTRA_PROJECT_ID", idProjectView[0]);
                intentTopLeft.putExtra("EXTRA_PROJECT_NAME", nameProjectView[0]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[0]);
                startActivity(intentTopLeft, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewTopRight:
                Intent intentTopRight = new Intent(this, ScrollingTaskActivity.class);
                intentTopRight.putExtra("EXTRA_PROJECT_ID", idProjectView[1]);
                intentTopRight.putExtra("EXTRA_PROJECT_NAME", nameProjectView[1]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[1]);
                startActivity(intentTopRight, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewMiddleTop:
                Intent intentMiddleTop = new Intent(this, ScrollingTaskActivity.class);
                intentMiddleTop.putExtra("EXTRA_PROJECT_ID", idProjectView[2]);
                intentMiddleTop.putExtra("EXTRA_PROJECT_NAME", nameProjectView[2]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[2]);
                startActivity(intentMiddleTop, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewMiddleBottom:
                Intent intentMiddleBottom = new Intent(this, ScrollingTaskActivity.class);
                intentMiddleBottom.putExtra("EXTRA_PROJECT_ID", idProjectView[3]);
                intentMiddleBottom.putExtra("EXTRA_PROJECT_NAME", nameProjectView[3]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[3]);
                startActivity(intentMiddleBottom, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewBottomLeft:
                Intent intentBottomLeft = new Intent(this, ScrollingTaskActivity.class);
                intentBottomLeft.putExtra("EXTRA_PROJECT_ID", idProjectView[4]);
                intentBottomLeft.putExtra("EXTRA_PROJECT_NAME", nameProjectView[4]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[4]);
                startActivity(intentBottomLeft, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewbottomRight:
                Intent intentBottomRight = new Intent(this, ScrollingTaskActivity.class);
                intentBottomRight.putExtra("EXTRA_PROJECT_ID", idProjectView[5]);
                intentBottomRight.putExtra("EXTRA_PROJECT_NAME", nameProjectView[5]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[5]);
                startActivity(intentBottomRight, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewMiddleLeft:
                Intent intentMiddleLeft = new Intent(this, ScrollingTaskActivity.class);
                intentMiddleLeft.putExtra("EXTRA_PROJECT_ID", idProjectView[6]);
                intentMiddleLeft.putExtra("EXTRA_PROJECT_NAME", nameProjectView[6]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[6]);
                startActivity(intentMiddleLeft, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.textViewMiddleRight:
                Intent intentMiddleRight = new Intent(this, ScrollingTaskActivity.class);
                intentMiddleRight.putExtra("EXTRA_PROJECT_ID", idProjectView[7]);
                intentMiddleRight.putExtra("EXTRA_PROJECT_NAME", nameProjectView[7]);
                Log.e("TAG", "idProjectIntent: " + idProjectView[7]);
                startActivity(intentMiddleRight, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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


                textViewArray[index].setText(myProjectList.getName());
                idProjectView[index] = myProjectList.getIdProject();
                nameProjectView[index] = myProjectList.getName();
                index = index + 1;
                Log.e("TAG", "readProjects: " + myProjectList.getName());
                Log.e("TAG", "readProjects: " + index);

                if (index == textViewCount) {
                    dbInst.close();
                    break;
                }




//                Maybe later to create TextView programmatically, but could be against Google guidelines.

//                ConstraintLayout constraintLayout = findViewById(R.id.activity_main);

//                TextView projectTextView = new TextView(this);
//                projectTextView.setText(myProjectList.getName());
//                projectTextView.setId(View.generateViewId());
//                Log.e("TAG", "readProjects: " + projectTextView.getId() );
//                projectTextView.setBackground(getDrawable(R.drawable.circle));
//                projectTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                projectTextView.setGravity(Gravity.CENTER);
//                projectTextView.setTextSize(20);
//                projectTextView.setTextColor(getColor(R.color.colorOrange));
//
//                constraintLayout.addView(projectTextView);
//
//                ConstraintSet constraintSet = new ConstraintSet();
//                constraintSet.clone(constraintLayout);
//
//                constraintSet.connect(projectTextView.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
//                constraintSet.connect(projectTextView.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
//                constraintSet.connect(projectTextView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
//                constraintSet.connect(projectTextView.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
//
//                constraintSet.applyTo(constraintLayout);


            }
            index = 0;
        }
    }

    private void SetOnClickListenerOnProject(TextView[] viewProject){
        for (TextView textViewProjectListener: viewProject) {
            textViewProjectListener.setOnClickListener(MainActivity.this);
        }
    }


}


