package com.omega.notification;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by vageesh on 4/23/15.
 */
public class GCMMainActivity extends Activity{
    ShareExternalServer appUtil;
    String regId;
    AsyncTask shareRegidTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.gcm_main);
        appUtil = new ShareExternalServer();

        regId = getIntent().getStringExtra("regId");
        Log.d("MainActivity", "regId: " + regId);

        final Context context = this;
        /*shareRegidTask = new AsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String result = appUtil.shareRegIdWithAppServer(context, regId);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                shareRegidTask = null;
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();
            }
            protected void onPreExecute() {
                //display progress dialog.

            }

        };*/

        class newAsycTask extends AsyncTask<String,String,String>{
            @Override
            protected String doInBackground(String... params) {
                String result = appUtil.shareRegIdWithAppServer(context, regId);
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                shareRegidTask = null;
                /*Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();*/
                /*Intent intent = new Intent(GCMMainActivity.this, TabActivity.class);
                startActivity(intent);*/
            }

        }
        new newAsycTask().execute(null,null,null);
    }

}
