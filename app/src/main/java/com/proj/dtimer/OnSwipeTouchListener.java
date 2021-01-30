package com.proj.dtimer;

// Detects Top swipes across a view.

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onSwipeBottom() {
    }

    public void onLongItemPress() {
    }

    public void onItemPress() {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 200;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            onItemPress();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            onLongItemPress();
        }

        @Override
        public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {
            float distanceX = motionEvent2.getX() - motionEvent1.getX();
            float distanceY = motionEvent2.getY() - motionEvent1.getY();

            /*
             * It's a swipe. Finger touches the screen --> motionEvent1(X,Y)
             * Finger leaves the screen --> motionEvent2(X,Y)
             * The difference in position between the two tell us it's a swipe
             * If Y > X then the swipe was done on the Y axis (Top or bottom)
             * If Y > 0 then it is a swipe to the bottom.
             */
            if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceY > 0) {
                    onSwipeBottom();
                }
                return true;
            }
            return false;
        }
    }
}
