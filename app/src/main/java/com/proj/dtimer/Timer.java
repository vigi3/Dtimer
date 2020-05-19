package com.proj.dtimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Timer extends AppCompatActivity {

    ProgressBar circleBar;
    CountDownTimer countDownTimer;
    EditText chrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        circleBar = findViewById(R.id.circleBar);
        chrono = findViewById(R.id.chrono);
        circleBar.setProgress(100);
        circleBar.setMax(100);
        startTimer(1);


    }

    public void startTimer(final int mins){

        countDownTimer = new CountDownTimer(60 * mins * 1000, 1000){
            @Override
            public void onTick(long leftTimeInMilliseconds){
                long seconds = leftTimeInMilliseconds / 6000;
                circleBar.setProgress((int)seconds);
                chrono.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));

            }

            @Override
            public void onFinish() {
                if(chrono.getText().equals("00:00")){
                    chrono.setText("STOP");
                }
                else{
                    chrono.setText("2:00");
                    circleBar.setProgress(60*mins);
                }
            }
        }.start();



    }

}
