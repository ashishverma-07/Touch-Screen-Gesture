package com.project.smartsensor.touchscreengesture.touchscreengesture;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 12/6/2017.
 */

public class ListInstalledApps extends ListActivity{


    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist=null;
    private AppAdapter listAdapter=null;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=(ListView) findViewById(R.id.list);
        packageManager=getPackageManager();

        new LoadApplications().execute();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app= applist.get(position);

        try{
            Intent intent = new Intent(this, SaveGestureActivity.class);
            intent.putExtra("PackageName", app.packageName);
            startActivity(intent);
            /*Intent intent= packageManager.getLeanbackLaunchIntentForPackage(app.packageName);

            if(intent != null){
                startActivity(intent);
            }
            */
        }catch (NoSuchMethodError e){
            Toast.makeText(this, "Launching "+app.loadLabel(packageManager)+ "...", Toast.LENGTH_LONG).show();

        }

        catch (ActivityNotFoundException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list){
        ArrayList<ApplicationInfo> applist=new ArrayList<ApplicationInfo>();

        for(ApplicationInfo info : list){
            try{
                if(packageManager.getLaunchIntentForPackage(info.packageName) != null){
                    applist.add(info);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  applist;
    }
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress=null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(packageManager.GET_META_DATA));
            listAdapter=new AppAdapter(ListInstalledApps.this,R.layout.listitem, applist);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listAdapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress=ProgressDialog.show(ListInstalledApps.this, null, "Loading app info ...");
            super.onPreExecute();
        }
    }
}
