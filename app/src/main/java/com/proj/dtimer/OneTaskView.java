package com.proj.dtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.transition.ChangeTransform;

import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class OneTaskView extends AppCompatActivity {

    private String taskNameView;
    private View includeLayout;
    private TextView nameTask;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Transition
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(300);
        changeBounds.setResizeClip(false);
        getWindow().setSharedElementEnterTransition(changeBounds);
        getWindow().setSharedElementReturnTransition(changeBounds);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);

        setContentView(R.layout.activity_one_task_view);
        includeLayout = findViewById(R.id.projectDialogLayout);
        includeLayout.setTransitionName("tasksList");
        taskNameView = getIntent().getStringExtra("EXTRA_TASK_NAME");
        nameTask = includeLayout.findViewById(R.id.detailName);
        nameTask.setText(taskNameView);
        getWindow().getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                includeLayout.setBackgroundColor(getColor(R.color.colorPrimary));
                nameTask.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
                TextView textView = includeLayout.findViewById(R.id.percentageCompletion);
                textView.setVisibility(View.GONE);
                transition.removeListener(this);
            }
        });

        getWindow().getSharedElementReturnTransition().addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionStart(Transition transition) {
                includeLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.project_box));
                nameTask.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

            }
        });

    }
}