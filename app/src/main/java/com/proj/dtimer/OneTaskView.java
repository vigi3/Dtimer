package com.proj.dtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.widget.Button;
import android.widget.TextView;


public class OneTaskView extends AppCompatActivity implements View.OnClickListener {

    private DataBaseManager dbInst;
    private String taskNameView;
    private View taskTextView;
    private int idTask;
    private TextView nameTask;
    private int paddingTitle;
    private Button priority1;
    private Button priority2;
    private Button priority3;


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
        paddingTitle = getIntent().getIntExtra("EXTRA_TITLE_NAME", 0);
        idTask = getIntent().getIntExtra("EXTRA_ID_TASK", 0);
        Log.d("OneTaskView", "onCreate: " + idTask);
        taskTextView.setPadding(100, 300, 0, 0);

        priority1 = findViewById(R.id.buttonPriority1);
        priority2 = findViewById(R.id.buttonPriority2);
        priority3 = findViewById(R.id.buttonPriority3);

        priority1.setOnClickListener(this);
        priority2.setOnClickListener(this);
        priority3.setOnClickListener(this);




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
                        startPostponedEnterTransition();

                        //Text needs a specific animation, it does not work with shared element transition
                        //A valueAnimator object is created, and the textSize property is used
                        TextView textView = (TextView) taskTextView;
                        textView.setText(taskNameView);
                        final float startSize = 15;
                        final float endSize = 30;
                        final int animationDuration = 200;
                        ValueAnimator animator = ObjectAnimator.ofFloat(textView, "textSize",startSize, endSize);
                        animator.setDuration(animationDuration);
                        animator.start();
//                        textView.setPadding(100, 300, 0, 0);

                        return true;
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonPriority1:
                dbInst = new DataBaseManager(this);
                final Tasks task = dbInst.showOneTask(idTask);
                task.setPriority(1);
                dbInst.updateTasks(task);
                dbInst.close();
                priority1.getBackground().setTint(getColor(R.color.colorRed));
                Log.e("OneTaskView", "readTaskAttributeOnclick: \n" + task.toString());

                break;

            case R.id.buttonPriority2:
                break;

            case R.id.buttonPriority3:
        }

    }
}
