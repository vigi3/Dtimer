package com.proj.dtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.transition.AutoTransition;
import android.transition.Explode;

import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;


public class OneTaskView extends AppCompatActivity {

    private String taskNameView;
    private View taskTextView;
    private TextView nameTask;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Transition
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setDuration(300);
        changeBounds.setResizeClip(true);
        changeBounds.addTarget("tasksList");
        getWindow().setSharedElementEnterTransition(changeBounds);
        getWindow().setSharedElementReturnTransition(changeBounds);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        postponeEnterTransition();

        setContentView(R.layout.activity_one_task_view);
        taskTextView = findViewById(R.id.taskTextView);
        taskTextView.setTransitionName("tasksList");
        taskNameView = getIntent().getStringExtra("EXTRA_TASK_NAME");
        TextView textView = (TextView) taskTextView;
//        textView.setText(taskNameView);
        scheduleStartPostponedTransition(taskTextView);
        getWindow().getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
//                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
                transition.removeListener(this);
            }
        });

        getWindow().getSharedElementReturnTransition().addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionStart(Transition transition) {
//                nameTask.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

            }
        });

    }

    //Check that the transition does know the EndValue of the Drawable, to smooth the animation
    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        Log.e("efr", "onPreDraw: true");
                        startPostponedEnterTransition();
                        return true;
                    }
                }
        );
    }
}
