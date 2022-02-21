package com.thanguit.tiushop.base;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SwipeToBackActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "GESTURE";
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDetector = new GestureDetector(this, new SwipeDetector());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TouchEvent dispatcher.
        if (gestureDetector != null) {
            if (gestureDetector.onTouchEvent(ev))
                // If the gestureDetector handles the event, a swipe has been
                // executed and no more needs to be done.
                return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener { // https://stackoverflow.com/a/38442055
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + e1.getX() + "|" + e2.getX() + "|");

            if ((e2.getX() - e1.getX()) > 0 && Math.abs(e2.getX() - e1.getX()) > 500) {
                finish();
                return true;
            } else {
                return false;
            }

//            // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
//            // then dismiss the swipe.
//            if (Math.abs(e1.getY() - e2.getY()) > 250)
//                return false;
//
//            // Swipe from left to right.
//            // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE)
//            // and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
//            if (e2.getX() - e1.getX() > 120 && Math.abs(velocityX) > 200) {
//                finish();
//                return true;
//            }
        }
    }
}
