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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextEnterDialogFragment.TextEnterDialogListener {

    TextView addProject;
    TextInputEditText projectName;
    private DataBaseManager dbInst;
    private Projects projects;
    private int index = 0;
    private int indexArray;
    private int indexArrayLong;
    private int projectListSize = 0;
    int[] idProjectView = {0, 0, 0, 0, 0, 0, 0, 0};
    String[] nameProjectView = {"","","","","","","",""};
    TextView[] textViewArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start and End animation
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);

        setContentView(R.layout.activity_main);
        addProject = findViewById(R.id.addProject);
        addProject.setOnClickListener(this);

        //Creation of 8 "Button" for each project
        textViewArray = new TextView[8];
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
        deactivateClickIfEmpty();

    }

    //OnClick method used for creation of a project, only possible if at least one TextView is available
    @Override
    public void onClick(View view){
        if (view.getId() == R.id.addProject) {
            if (!(projectListSize >= 8)){
                textEnterDialogFragment();
            }
        }
    }

    //Creation of Dialog box to add a project name
    public void textEnterDialogFragment() {
        TextEnterDialogFragment textDialog = new TextEnterDialogFragment();
        textDialog.show(getSupportFragmentManager(), "TextEnterDialogFragment");
    }

    //Dialog box positive and negative button, add or cancel the creation of a project
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Positive Event is return by interface TextEnterDialogListener so logic can be done here
        projectName = dialog.getDialog().findViewById(R.id.textInput);
        if (TextUtils.isEmpty(projectName.getEditableText()) || projectName.getError() == null) {
            // if dialogBox empty, restart dialogBox
            textEnterDialogFragment();
            TextInputLayout textInputLayout = dialog.getDialog().findViewById(R.id.textInputLayout);
            textInputLayout.setHint("Name missing !");
            Log.e("Dialog", "Project name is null/empty or characters > 10");

        }
        else {
            projects = new Projects();
            projects.setName(String.valueOf(projectName.getText()));
            dbInst = new DataBaseManager(this);
            dbInst.addProject(projects);
            dbInst.close();
            dialog.dismiss();
            readProjects();
            deactivateClickIfEmpty();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();
    }



    public void readProjects() {
        dbInst = new DataBaseManager(this);
        projectListSize = 0;
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

                if (index == projectsList.size()) {
                    projectListSize = projectsList.size();
                    dbInst.close();
                    index = 0;
                    break;
                }
            }
        }
    }

    //Set OnClick and OnLongClick Listener For each project
    private void SetOnClickListenerOnProject(TextView[] viewProject){
        indexArray = 0;
        indexArrayLong = 0;
        for (final TextView textViewProjectListener: viewProject) {
            textViewProjectListener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (TextView oneTextView: textViewArray ){
                        Log.d("TAG", "indexArray: " + indexArray);

                        //Detect which project is clicked on, send id+Name to ScrollingTaskActivity and launch it.
                        if (view.getId() == oneTextView.getId()) {
                            Intent intentProject = new Intent(MainActivity.this, ScrollingTaskActivity.class);
                            intentProject.putExtra("EXTRA_PROJECT_ID", idProjectView[indexArray]);
                            intentProject.putExtra("EXTRA_PROJECT_NAME", nameProjectView[indexArray]);
                            Log.e("TAG", "idProjectIntent: " + idProjectView[indexArray]);
                            startActivity(intentProject, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            indexArray = 0;
                            break;
                        }
                        else {
                            indexArray++;
                        }
                    }
                }
            });
            textViewProjectListener.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    for (final TextView oneTextViewLong: textViewArray ){
                        Log.d("TAG", "indexArrayLong: " + indexArrayLong);

                        //Delete Project and all tasks associated with, set the Snackbar
                        if (view.getId() == oneTextViewLong.getId()) {
                            view.performHapticFeedback(1);
                            view.setElevation(5f);
                            view.setAlpha(0.5f);
                            Snackbar snack = Snackbar.make(view, "Delete ?", Snackbar.LENGTH_LONG).addCallback(new Snackbar.Callback(){
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    super.onDismissed(transientBottomBar, event);
                                    if (event != DISMISS_EVENT_ACTION) {
                                        view.setAlpha(1f);
                                    }
                                }
                            }).setAction("DELETE", new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
                                    dbInst = new DataBaseManager(getApplicationContext());
                                    List<Tasks> taskListToDelete = dbInst.showAllTasksFromOneProject(idProjectView[indexArrayLong]);
                                    if (!taskListToDelete.isEmpty()) {
                                        for (Tasks task: taskListToDelete) {
                                            dbInst.delTaskById(task);
                                        }
                                    }
                                    dbInst.delProjectById(idProjectView[indexArrayLong]);
                                    dbInst.close();
                                    oneTextViewLong.setText(null);
                                    oneTextViewLong.setAlpha(1f);
                                    deactivateClickIfEmpty();
                                    Log.d("TAG", "indexArrayLongDeleted: " + indexArrayLong);
                                    indexArrayLong = 0;
                                    projectListSize --;
                                }
                            }).setActionTextColor(getColor(R.color.colorRed));
                            snack.show();
                            break;
                        }
                        else {
                            indexArrayLong++;
                        }
                    }
                    return true;
                }
            });
        }
    }

    //Avoid triggering OnClick when textView Project is empty
    private void deactivateClickIfEmpty() {
        for (TextView textViewClick: textViewArray) {
            if (!(textViewClick.length() > 0)) {
                Log.d("ifEmpty", "textViewClick < 0: " + textViewClick.getText().toString().isEmpty());
                textViewClick.setEnabled(false);
            }
            else {
                Log.d("ifEmpty", "textViewClick > 0: " + textViewClick.getText().toString().isEmpty());
                textViewClick.setEnabled(true);
            }
        }
    }
}


