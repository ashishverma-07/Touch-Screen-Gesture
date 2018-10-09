package com.project.smartsensor.touchscreengesture.touchscreengesture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addButtonClick(View view){
        Intent addGesture = new Intent(MainActivity.this,ListInstalledApps.class);
        startActivity(addGesture);
    }
    public void testButtonClick(View view){
        Intent testGesture = new Intent(MainActivity.this,GestureActivity.class);
        startActivity(testGesture);
    }
    public void editButtonClick(View view){
        Intent editGesture = new Intent(this,GestureDisplay.class);
        startActivity(editGesture);
    }
}
