package com.project.smartsensor.touchscreengesture.touchscreengesture;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shubham on 11/17/2017.
 */

public class GestureActivity extends Activity{
    private GestureLibrary gLib;
    private static final String TAG = "GestureActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        openOptionsMenu();
        gLib = GestureLibraries.fromFile(getExternalFilesDir(null)+ "/" + "gesture.txt");
        gLib.load();

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(handleGestureListener);
        gestures.setGestureStrokeAngleThreshold(90.0f);
    }
    private GestureOverlayView.OnGesturePerformedListener handleGestureListener = new GestureOverlayView.OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
            ArrayList<Prediction> predictions = gLib.recognize(gesture);
            if (predictions.size() > 0) {
                Prediction prediction = predictions.get(0);
                if (prediction.score > 6)  {
                    startActivity( getPackageManager().getLaunchIntentForPackage(prediction.name));
                }  else
                {
                    Toast.makeText(GestureActivity.this,"No Apps found with this gesture.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
    }
