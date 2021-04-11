package com.proj.dtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
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

import com.google.android.material.button.MaterialButton;


public class OneTaskView extends AppCompatActivity implements View.OnClickListener {

    private DataBaseManager dbInst;
    private String taskNameView;
    private View taskTextView;
    private int idTask;
    private int priority;
    private Drawable initBackground;
    private TextView nameTask;
    private int paddingTitle;
    private Button priorityMain;
    private Button priorityImportant;
    private Button priorityNormal;


    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Transition
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        ChangeBounds changeBounds = new ChangeBounds();
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
        priority = getIntent().getIntExtra("EXTRA_PRIORITY_TASK", 0);

        Log.d("OneTaskView", "onCreate idTask: " + idTask);
        Log.d("OneTaskView", "onCreate priority: " + priority);

        taskTextView.setPadding(100, 300, 0, 0);

        priorityMain = findViewById(R.id.buttonPriorityMain);
        priorityImportant = findViewById(R.id.buttonPriorityImportant);
        priorityNormal = findViewById(R.id.buttonPriorityNormal);

        priorityMain.setOnClickListener(this);
        priorityImportant.setOnClickListener(this);
        priorityNormal.setOnClickListener(this);

        initBackground = ContextCompat.getDrawable(this, R.drawable.button_priority_drawable);

        setBackgroundPriority(priority);



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
                        Log.d("OneTaskView", "ScheduleStartPostponedTransition successful");
//                        textView.setPadding(100, 300, 0, 0);

                        return true;
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonPriorityMain:
                dbInst = new DataBaseManager(this);
                final Tasks task = dbInst.showOneTask(idTask);
                task.setPriority(1);
                dbInst.updateTasks(task);
                dbInst.close();
                setBackgroundPriority(task.getPriority());
                Log.d("OneTaskView", "readTaskPriorityOnclick: \n" + task.toString());

                break;

            case R.id.buttonPriorityImportant:
                dbInst = new DataBaseManager(this);
                final Tasks task2 = dbInst.showOneTask(idTask);
                task2.setPriority(2);
                dbInst.updateTasks(task2);
                dbInst.close();
                setBackgroundPriority(task2.getPriority());
                Log.d("OneTaskView", "readTaskPriorityOnclick: \n" + task2.toString());
                break;

            case R.id.buttonPriorityNormal:
                dbInst = new DataBaseManager(this);
                final Tasks task3 = dbInst.showOneTask(idTask);
                task3.setPriority(0);
                dbInst.updateTasks(task3);
                dbInst.close();
                setBackgroundPriority(task3.getPriority());
                Log.d("OneTaskView", "readTaskPriorityOnclick: \n" + task3.toString());
                break;
        }

    }

    public void setBackgroundPriority(int intPriority) {

        switch (intPriority){
            case 0: //Normal - White
                priorityNormal.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
                priorityNormal.getBackground().setTint(getColor(R.color.colorWhite));
                Log.d("OneTaskView", "setBackgroundPriority: normal " + initBackground);
                priorityImportant.getBackground().setTint(getColor(R.color.colorPrimary));
                priorityMain.getBackground().setTint(getColor(R.color.colorPrimary));
                break;
            case 1: // Urgent - Red
                priorityMain.getBackground().setTint(getColor(R.color.colorRed));
                Log.d("OneTaskView", "setBackgroundPriority: urgent " + initBackground);
                priorityImportant.getBackground().setTint(getColor(R.color.colorPrimary));
                priorityNormal.getBackground().setTint(getColor(R.color.colorPrimary));
                break;
            case 2: // Important - Yellow
                priorityImportant.getBackground().setTint(getColor(R.color.colorYellowSpice));
                Log.d("OneTaskView", "setBackgroundPriority: important " + initBackground);
                priorityNormal.getBackground().setTint(getColor(R.color.colorPrimary));
                priorityMain.getBackground().setTint(getColor(R.color.colorPrimary));
                break;
        }
    }

}
