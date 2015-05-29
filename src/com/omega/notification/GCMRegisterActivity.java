package com.omega.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.ArrayList;

public class GCMRegisterActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    Button btnGCMRegister;
    Button btnAppShare;
    GoogleCloudMessaging gcm;
    Context context;
    String regId;

    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";

    static final String TAG = "Register Activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);

        context = getApplicationContext();

        /*btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
        //btnGCMRegister.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View arg0) {
                if (TextUtils.isEmpty(regId)) {
                    regId = registerGCM();
                    Log.d("RegisterActivity", "GCM RegId: " + regId);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Already Registered with GCM Server!",
                            Toast.LENGTH_LONG).show();
                }
            //}
        //});

        btnAppShare = (Button) findViewById(R.id.btnAppShare);
        //btnAppShare.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View arg0) {
                if (TextUtils.isEmpty(regId)) {
                    Toast.makeText(getApplicationContext(), "RegId is empty!",
                            Toast.LENGTH_LONG).show();
                } else {
                    *//*Intent i = new Intent(getApplicationContext(),
                            GCMRegisterActivity.class);*//*
                    Intent i = new Intent(getApplicationContext(),
                            GCMMainActivity.class);
                    i.putExtra("regId", regId);
                    Log.d("RegisterActivity",
                            "onClick of Share: Before starting main activity.");
                    startActivity(i);
                    finish();
                    Log.d("RegisterActivity", "onClick of Share: After finish.");
                }
            //}
        //});*/

        this.registerGCMActivity();
        //this.shareWithServerActivity();
    }

    protected void onPostExecute(String result) {
        /*Intent intent = new Intent(GCMRegisterActivity.this, TabActivity.class);
        startActivity(intent);*/
        //this.shareWithServerActivity();
    }
    public void registerGCMActivity(){
        if (TextUtils.isEmpty(regId)) {
            regId = registerGCM();
            Log.d("RegisterActivity", "GCM RegId: " + regId);
        } else {
            /*Toast.makeText(getApplicationContext(),
                    "Already Registered with GCM Server!",
                    Toast.LENGTH_LONG).show();*/
        }
    }

    public void shareWithServerActivity(){
        if (TextUtils.isEmpty(regId)) {
            /*Toast.makeText(getApplicationContext(), "RegId is empty!",
                    Toast.LENGTH_LONG).show();*/
        } else {
                    /*Intent i = new Intent(getApplicationContext(),
                            TestActivity.class);*/
            Intent i = new Intent(getApplicationContext(),
                    GCMMainActivity.class);
            i.putExtra("regId", regId);
            Log.d("RegisterActivity",
                    "onClick of Share: Before starting main activity.");
            startActivity(i);
            finish();
            Log.d("RegisterActivity", "onClick of Share: After finish.");
        }
    }
    public String registerGCM() {
        //Context context = ctx.getApplicationContext();
        gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId(context);

        if (TextUtils.isEmpty(regId)) {

            registerInBackground();

            Log.d("RegisterActivity",
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            /*Toast.makeText(getApplicationContext(),
                    "RegId already available. RegId: " + regId,
                    Toast.LENGTH_LONG).show();*/
        }
        return regId;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getSharedPreferences(
                GCMRegisterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    private void registerInBackground() {
        /*new AsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;

                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("RegisterActivity", "Error: " + msg);
                }
                Log.d("RegisterActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();
            }
        }.execute(null, null, null);*/
        class MyAsyncTask extends AsyncTask<String,String,String>{
            @Override
            protected String doInBackground(String... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;

                    storeRegistrationId(context, regId);
                    shareWithServerActivity();
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("RegisterActivity", "Error: " + msg);
                }
                Log.d("RegisterActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                /*Toast.makeText(getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();*/
                //shareWithServerActivity();
                /*Intent intent = new Intent(GCMRegisterActivity.this,TabActivity.class); //comented for notification test
                startActivity(intent);*/
            }
        }

        new MyAsyncTask().execute(null,null,null);
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                GCMRegisterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        /*nameValuePairs.add(new BasicNameValuePair("name",fullName));
        nameValuePairs.add(new BasicNameValuePair("email", email));*/
        nameValuePairs.add(new BasicNameValuePair("gcm_regid",regId));

        String statusString = "";

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            //HttpPost httppost = new HttpPost("http://10.0.0.84/test/gcm_main.php");
            HttpPost httppost = new HttpPost("http://rsdoddamani.co.in/app/php_scripts/gcm_main.php");
            //HttpPost httppost = new HttpPost("http://192.168.1.2/test/gcm_main.php");
            //HttpPost httppost = new HttpPost("http://localhost/test/gcm_main.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            //is = entity.getContent();
            Log.e("pass 1a", "connection success ");
            Log.i(TAG,"form posted to php");

        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
                /*Toast.makeText(getApplicationContext(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();*/
            statusString = "Invalid IP Address";
            //return statusString;
        }
    }
}
