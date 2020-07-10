package com.proj.dtimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class Timer extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    ProgressBar circleBar;
    CountDownTimer countDownTimer = null;
    TextView chrono;
    Button startTime;
    int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
//        circleBar = findViewById(R.id.circleBar);
        chrono = findViewById(R.id.chrono);
//        circleBar.setProgress(100);
//        circleBar.setMax(100);
        startTime = findViewById(R.id.startButton);
//        start(1);

        startTime.setOnTouchListener(this);
        startTime.setOnClickListener(this);


    }



    /* This function check if a timer already exist, cancel it and
       instance a timer, with 2 parameters in milliseconds

     */
    public void startTimer() {
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = Math.toIntExact(millisUntilFinished) / 1000;
                chrono.setText(seconds + "");
//                circleBar.setProgress(100/seconds);

            }
            public void onFinish() {
                chrono.setText("FINIS !!");
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onClick(View view){
//            startTimer();
    }


    /* Used onTouch function instead of onClick because the onClick Event in
       timer_scene.xml(MotionScene) "override" the OnClickListener, so OnClickListener
       is not triggered
    */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction())
            startTimer();
        return false;
    }
}
