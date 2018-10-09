package com.project.smartsensor.touchscreengesture.touchscreengesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Shubham on 11/17/2017.
 */

public class SaveGestureActivity extends Activity {
    private GestureLibrary gLib;
    private static final String TAG = "SaveGestureActivity";
    private boolean mGestureDrawn;
    private Gesture mCurrentGesture;
    private String mGesturename;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_gesture);
        gLib = GestureLibraries.fromFile(getExternalFilesDir(null)+"/"+"gesture.txt");
        gLib.load();
        GestureOverlayView gestures = (GestureOverlayView)findViewById(R.id.save_gesture);
        gestures.addOnGestureListener(mGestureListener);
        resetEverything();
    }
    private GestureOverlayView.OnGestureListener mGestureListener= new GestureOverlayView.OnGestureListener() {
        @Override
        public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent){
            mGestureDrawn = true;
        }
        @Override
        public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
            mCurrentGesture = gestureOverlayView.getGesture();
        }

        @Override
        public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

        }

        @Override
        public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

        }
    };

    public void saveName(View view){
        if(mGestureDrawn){
            mGesturename = getIntent().getStringExtra("PackageName");
            saveGesture();
            finish();
        }
        else
        {
            Toast.makeText(this,"Please draw gesture first",Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View view){
        reDrawGestureView();
    }

    private void saveGesture(){

        gLib.addGesture(mGesturename,mCurrentGesture);
        if(gLib.save()){
            Toast.makeText(this,"Gesture saved",Toast.LENGTH_SHORT).show();
        }

        reDrawGestureView();
    }

    public void resetEverything(){
        mGestureDrawn = false;
        mCurrentGesture = null;
        mGesturename = "";
    }

    private void reDrawGestureView(){
        setContentView(R.layout.save_gesture);
        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.save_gesture);
        gestures.removeAllOnGestureListeners();
        gestures.removeAllOnGestureListeners();
        gestures.addOnGestureListener(mGestureListener);
        resetEverything();
    }
}
