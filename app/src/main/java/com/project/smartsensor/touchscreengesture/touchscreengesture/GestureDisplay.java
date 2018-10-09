package com.project.smartsensor.touchscreengesture.touchscreengesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Shubham on 12/6/2017.
 */

public class GestureDisplay extends Activity{
    private static final String TAG = "GestureListActivity";
    private String mCurrentGestureName, newName;
    private ListView mGestureListView;
    private static ArrayList<GestureHolder> mGestureList;
    private GestureAdapter mGestureAdapter;
    private GestureLibrary gLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_gesture);
        mGestureListView = (ListView) findViewById(R.id.gesture_list);
        mGestureList = new ArrayList<GestureHolder>();
        createList();
        mGestureAdapter = new GestureAdapter(mGestureList, GestureDisplay.this);
        mGestureListView.setLongClickable(true);
        mGestureListView.setAdapter(mGestureAdapter);

        registerForContextMenu(mGestureListView);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.display_gesture);
        mGestureListView = (ListView) findViewById((R.id.gesture_list));
        mGestureList =new ArrayList<GestureHolder>();
        createList();
        mGestureAdapter = new GestureAdapter(mGestureList, GestureDisplay.this);
        mGestureListView.setLongClickable(true);
        mGestureListView.setAdapter(mGestureAdapter);

        registerForContextMenu(mGestureListView);

    }

    private void createList() {
        try {
            mGestureList = new ArrayList<GestureHolder>();
            gLib = GestureLibraries.fromFile( getExternalFilesDir(null) + "/" + "gesture.txt");
            gLib.load();
            Set<String> gestureSet = gLib.getGestureEntries();
            for(String gestureName: gestureSet){
                ArrayList<Gesture> list = gLib.getGestures(gestureName);
                for(Gesture g:list){
                    mGestureList.add(new GestureHolder(g, gestureName));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateMenu(View view){
       // LinearLayout vwParentRow =(LinearLayout) view.getParent().getParent();
        TextView tv = (TextView)findViewById(R.id.gesture_name_ref);
        mCurrentGestureName = tv.getText().toString();
        PopupMenu popupMenu = new PopupMenu( this, view);
        popupMenu.getMenuInflater().inflate(R.menu.gesture_item_options, popupMenu.getMenu());
        popupMenu.show();
    }

    /*
    public void renameButtonClick(MenuItem item){
        AlertDialog.Builder namePopup = new AlertDialog.Builder(this);
        namePopup.setTitle(R.string.enter_new_name);
        final EditText nameField = new EditText(this);
        namePopup.setView(nameField);

        namePopup.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!nameField.getText().toString().matches("")){
                    newName = nameField.getText().toString();
                    saveGesture();
                }
                else {
                    renameButtonClick(null);
                    //  Toast.makeText(this,getString(R.string.invalid_name),Toast.LENGTH_SHORT).show();
                }
            }
        });
        namePopup.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newName = "";
                mCurrentGestureName = "";
                return;
            }
        });
        namePopup.show();
    }
    */
    private void saveGesture(){
        ArrayList<Gesture> list = gLib.getGestures(mCurrentGestureName);
        if(list.size() > 0){
            gLib.removeEntry(mCurrentGestureName);
            gLib.addGesture(newName, list.get(0));
            if(gLib.save()){
                onResume();
            }
        }
        newName="";
    }
    public void deleteAndNew(MenuItem item) {
        gLib.removeEntry(mCurrentGestureName);
        gLib.save();
        mCurrentGestureName = "";
        Intent draw = new Intent(this,ListInstalledApps.class);
        startActivity(draw);
    }
        public void deleteButtonClick(MenuItem item){
        gLib.removeEntry(mCurrentGestureName);
        gLib.save();
        mCurrentGestureName = "";
        onResume();
    }

}
