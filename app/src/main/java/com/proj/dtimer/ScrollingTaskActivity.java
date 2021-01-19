package com.proj.dtimer;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class ScrollingTaskActivity extends AppCompatActivity {

    private DataBaseManager dbInst;
    private ListView taskList;
    int projectIdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_task);
        taskList = findViewById(R.id.taskList);
        projectIdView = getIntent().getIntExtra("EXTRA_PROJECT_ID", 0);
        Log.e("TAG", "readScroll: " + projectIdView);

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

            }
        });

        readTasks(projectIdView);

    }

    public void readTasks(int projectIdView){
        dbInst = new DataBaseManager(this);

        final List<Tasks> tasks = dbInst.showAllTasksFromOneProject(projectIdView);
//        final List<Tasks> tasks = dbInst.showAllTasks();

        Log.e("TAG", "readTasks: " + projectIdView);

        if(tasks.isEmpty()){
            taskList.setVisibility(View.GONE);
        }
        else {
            taskList.setVisibility(View.VISIBLE);
        }

        taskList.setAdapter(new TaskListAdapter(this, tasks));

        dbInst.close();

    }


}