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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        circleBar = findViewById(R.id.circleBar);
        chrono = findViewById(R.id.chrono);
        circleBar.setProgress(100);
        circleBar.setMax(100);
        startTime = findViewById(R.id.startButton);
//        start(1);

        startTime.setOnClickListener(this);


    }



    public void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = Math.toIntExact(millisUntilFinished) / 1000;
                chrono.setText("Secondes Restantes: " + seconds);
                circleBar.setProgress(seconds);
            }
            public void onFinish() {
                chrono.setText("FINIS !!");
            }
        };
        countDownTimer.start();
    }

//    public final int start(final int minutes){
//
//        countDownTimer = new CountDownTimer(60 * minutes * 1000, 1000){
//            @Override
//            public void onTick(long millisUntilFinished){
//                long seconds = millisUntilFinished / 6000;
//                circleBar.setProgress((int)seconds);
//                chrono.setText(R.string.secRemain + String.valueOf(seconds));
//
//            }
//
//            @Override
//            public void onFinish() {
//                chrono.setText("Done!");
////                if(chrono.getText().equals("00:00")){
////                    chrono.setText(R.string.stop);
////                }
////                else{
////                    chrono.setText(R.string.minutes);
////                    circleBar.setProgress(60 * mins);
////                }
//            }
//        };
//
//
//    return minutes;
//    }


    @Override
    public void onClick(View view){

        startTimer();

    }
}
