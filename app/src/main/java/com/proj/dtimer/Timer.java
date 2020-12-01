package com.proj.dtimer;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Timer extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, Request.Method {

    CountDownTimer countDownTimer = null;
    TextView chrono;
    Button startTime;
    int seconds = 0;
    String token;
    String url;
    String rawImageUrl;
    RequestQueue queue;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        chrono = findViewById(R.id.chrono);
        startTime = findViewById(R.id.startButton);

        context = Timer.this;
        queue = Volley.newRequestQueue(context);
        startTime.setOnTouchListener(this);
        chrono.setOnClickListener(this);
        token = getString(R.string.UNSPLASH_API_TOKEN);
        url = "https://api.unsplash.com/photos/random/?client_id=" + token;
        imageCall();



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
        Intent intent = new Intent(this, ScrollingTaskActivity.class);
        if (MotionEvent.ACTION_UP == event.getAction())
//            startTimer();
              startActivity(intent);
//            getImageApiBitmap(rawImageUrl);

        return false;
    }


    // API call
    public void imageCall(){
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Yup", "yaa " + response.getJSONObject("urls").get("raw"));
                    rawImageUrl = response.getJSONObject("urls").get("small").toString();           // CHANGE TO FULL !!!!
//                    imageView.setImageBitmap(getImageApiBitmap(rawImageUrl));
                    Log.e("Yap", "ah " + rawImageUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Yup", "Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Yup", "ERROR: " + error);
            }

        });
        queue.add(jsonObjectRequest);


    }



// Need to extend Runnable
//    @Override
//    public void run() {
//        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
//        // Fetch image from Api url
//        public Bitmap getImageApiBitmap(String rawImageUrl) {
//            Bitmap bitmap = null;
//            try {
//                URL aURL = new URL(rawImageUrl);
//                URLConnection connection = aURL.openConnection();
//                connection.connect();
//                InputStream inputStream = connection.getInputStream();
//                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
//                bufferedInputStream.close();
//                inputStream.close();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                Log.e("Bitmap", "Error getting bitmap from Raw url API", e);
//            }
//            return bitmap;
//        }
//    }
}


