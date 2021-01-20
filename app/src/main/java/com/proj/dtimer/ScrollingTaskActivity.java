package com.proj.dtimer;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ScrollingTaskActivity extends AppCompatActivity implements TextEnterDialogFragment.TextEnterDialogListener {

    private DataBaseManager dbInst;
    private ListView taskList;
    private Tasks tasks;
    int projectIdView;
    TextInputEditText taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_task);
        taskList = findViewById(R.id.taskList);
        projectIdView = getIntent().getIntExtra("EXTRA_PROJECT_ID", 0);
        Log.e("Scrollingtask", "readScroll: " + projectIdView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("DTimer");
        toolBarLayout.setExpandedTitleColor(getColor(R.color.colorOrange));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                textEnterDialogTaskFragment();
            }
        });

        readTasks(projectIdView);

    }

    public void readTasks(int projectIdView){
        dbInst = new DataBaseManager(this);

        final List<Tasks> tasks = dbInst.showAllTasksFromOneProject(projectIdView);
//        final List<Tasks> tasks = dbInst.showAllTasks();

        Log.e("ScrollingTask", "readTaskProjectId: " + projectIdView);

        if(tasks.isEmpty()){
            taskList.setVisibility(View.GONE);
        }
        else {
            taskList.setVisibility(View.VISIBLE);
            taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Object tasksList = adapterView.getItemAtPosition(position);
                    Log.e("ScrollingTask", "readTaskAttributeOnclick: \n" + tasksList.toString());

                }
            });

            taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Object tasksList = adapterView.getItemAtPosition(position);
                    Log.e("ScrollingTask", "readTaskAttributeOnHeldclick: \n" + tasksList.toString());
                    return true;
                }
            });
        }

        taskList.setAdapter(new TaskListAdapter(this, tasks));

        dbInst.close();

    }

    public void textEnterDialogTaskFragment() {
        // Create an instance of the dialog fragment and show it
        TextEnterDialogFragment textDialog = new TextEnterDialogFragment();
        textDialog.show(getSupportFragmentManager(), "TextEnterDialogTaskFragment");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Positive Event is return by interface TextEnterDialogListener so logic can be done here
        taskName = dialog.getDialog().findViewById(R.id.textInput);
        if (TextUtils.isEmpty(taskName.getEditableText())) {
            // if dialogBox empty, restart dialogBox
            textEnterDialogTaskFragment();
            TextInputLayout textInputLayout = dialog.getDialog().findViewById(R.id.textInputLayout);
            textInputLayout.setHint("Name missing !");
            Log.e("Dialog", "Task name is null/empty");
        }
        else {
            tasks = new Tasks();
            dbInst = new DataBaseManager(getApplicationContext());
            tasks.setName(String.valueOf(taskName.getText()));
            tasks.setIdProject(projectIdView);
            tasks.setState(0);
            dbInst.addTask(tasks);
            dbInst.close();
            dialog.dismiss();
            readTasks(projectIdView);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}