package com.proj.dtimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity implements View.OnClickListener {

    ProgressBar circleBar;
    CountDownTimer countDownTimer = null;
    TextView chrono;
    Button startTime;
    int seconds = 0;
    boolean timerOn = false;

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

        startTime.setOnClickListener(this);


    }



    public void startTimer() {
        timerOn = true;
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = Math.toIntExact(millisUntilFinished) / 1000;
                chrono.setText(seconds + "");
//                circleBar.setProgress(100/seconds);

            }
            public void onFinish() {
                chrono.setText("FINIS !!");
                timerOn = false;
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onClick(View view){
            startTimer();
    }


}
