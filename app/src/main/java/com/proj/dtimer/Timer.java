package com.proj.dtimer;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    CountDownTimer countDownTimer = null;
    TextView chrono;
    Button startTime;
    int seconds = 0;
//    float textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        chrono = findViewById(R.id.chrono);
        startTime = findViewById(R.id.startButton);
//        textSize = chrono.getTextSize();


        startTime.setOnTouchListener(this);
        chrono.setOnClickListener(this);


    }


    /* This function check if a timer already exist, cancel it and
       instance a timer, with 2 parameters in milliseconds

     */
    public void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
//            chrono.setTextSize(textSize);

        }
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = Math.toIntExact(millisUntilFinished) / 1000;
                chrono.setText(seconds + "");

            }

            public void onFinish() {
                chrono.setTextSize(15);
                chrono.setText("done");
            }
        };
        countDownTimer.start();
    }


    @Override
    public void onClick(View view) {

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


