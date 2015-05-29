package com.omega.notification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vageesh on 5/6/15.
 */
public class GCMRegHelper {
    GoogleCloudMessaging gcm;
    Context context;
    String regId;
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    static final String TAG = "Register Activity";

    public GCMRegHelper(Context context) {
        this.context = context;
    }

    public void registerGCMActivity(Context ctx, SharedPreferences pref)
    {
        if (TextUtils.isEmpty(this.regId))
        {
            regId = registerGCM(ctx, pref);
            Log.d("RegisterActivity", "GCM RegId: " + this.regId);
        }
        else
        {
            Toast.makeText(ctx.getApplicationContext(), "Already Registered with GCM Server!", Toast.LENGTH_LONG).show();
        }
    }

    public Intent shareWithServerActivity(Context ctx, SharedPreferences pref)
    {
        Log.d("RegisterActivity", "Reg Id"+this.regId);
        //String regId = "";
        if (TextUtils.isEmpty(this.regId))
        {
            Log.d("RegisterActivity", "Empty register id");
            Toast.makeText(ctx.getApplicationContext(), "RegId is empty!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent i = new Intent(context.getApplicationContext(), GCMMainActivity.class);

            i.putExtra("regId", this.regId);
            Log.d("RegisterActivity", "onClick of Share: Before starting main activity.");
            Log.d("Reg id inside sharewithserver:",this.regId);
            Log.d("RegisterActivity", "onClick of Share: After finish.");
            regId = this.regId;
            return i;
        }
        return null;
    }

    public String registerGCM(Context context, SharedPreferences pref)
    {
        gcm = GoogleCloudMessaging.getInstance(context);
        regId = getRegistrationId(context, pref);
        if (TextUtils.isEmpty(this.regId))
        {
            registerInBackground(context, pref);

            Log.d("RegisterActivity", "registerGCM - successfully registered with GCM server - regId: " + this.regId);
        }
        else
        {
            Toast.makeText(context.getApplicationContext(), "RegId already available. RegId: " + this.regId, Toast.LENGTH_LONG).show();
        }
        return this.regId;
    }

    private String getRegistrationId(Context context, SharedPreferences prefs)
    {
        String registrationId = prefs.getString("regId", "");
        if (registrationId.isEmpty())
        {
            Log.i("Register Activity", "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt("appVersion", Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context.getApplicationContext());
        if (registeredVersion != currentVersion)
        {
            Log.i("Register Activity", "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.d("RegisterActivity", "I never expected this! Going down, going down!" + e);

            throw new RuntimeException(e);
        }
    }

    private void registerInBackground(Context context1, final SharedPreferences prefs)
    {
        final Context ctx = context.getApplicationContext();
        class MyAsyncTask extends AsyncTask<String,String,String>{
            @Override
            protected String doInBackground(String... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context.getApplicationContext());
                        Log.d("Helper activity","instantiating gcm");
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);

                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;

                    storeRegistrationId(context.getApplicationContext(), regId,prefs);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("RegisterActivity", "Error: " + msg);
                }
                Log.d("RegisterActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //shareWithServerActivity(context.getApplicationContext(),prefs);
                Toast.makeText(context.getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();
            }

            /*private void storeRegistrationId(Context context, String regId, SharedPreferences prefs)
            {
                int appVersion = getAppVersion(context);
                Log.i("Register Activity", "Saving regId on app version " + appVersion);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("regId", regId);
                editor.putInt("appVersion", appVersion);
                editor.commit();

                ArrayList<NameValuePair> nameValuePairs = new ArrayList();



                nameValuePairs.add(new BasicNameValuePair("gcm_regid", regId));

                String statusString = "";
                try
                {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://10.0.0.84/test/gcm_main.php");
                    //HttpPost httppost = new HttpPost("http://192.168.1.2/test/gcm_main.php");

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    Log.e("pass 1a", "connection success ");
                    Log.i("Register Activity", "form posted to php");
                }
                catch (Exception e)
                {
                    Log.e("Fail 1", e.toString());


                    statusString = "Invalid IP Address";
                }
            }*/


        }

        System.out.println("-------------------------------------Before call---------------------------------------------");
        new MyAsyncTask().execute(null,null,null);
        //test(context,prefs);
        System.out.println("-------------------------------------After call---------------------------------------------");
    }


    public String test(Context ctx,SharedPreferences prefs){
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context.getApplicationContext());
                Log.d("Helper activity","instantiating gcm");
            }
            regId = gcm.register(Config.GOOGLE_PROJECT_ID);
            Intent intent = new Intent(context.getApplicationContext(),this.getClass());
            final String registrationId = intent.getStringExtra("registration_id");
            System.out.println("REG id :-------------------------->"+registrationId);
            Log.d("RegisterActivity", "registerInBackground - regId: "
                    + regId);
            msg = "Device registered, registration ID=" + regId;

            storeRegistrationId(context.getApplicationContext(), regId,prefs);

        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
            Log.d("RegisterActivity", "Error: " + msg);
        }
        Log.d("RegisterActivity", "AsyncTask completed: " + msg);
        return msg;
    }

    public void storeRegistrationId(Context context, String regId, SharedPreferences prefs)
    {
        int appVersion = getAppVersion(context);
        Log.i("Register Activity", "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("regId", regId);
        editor.putInt("appVersion", appVersion);
        editor.commit();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList();



        nameValuePairs.add(new BasicNameValuePair("gcm_regid", regId));

        String statusString = "";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://rsdoddamani.co.in/app/php_scripts/gcm_main.php");
            //HttpPost httppost = new HttpPost("http://192.168.1.2/test/gcm_main.php");

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            Log.e("pass 1a", "connection success ");
            Log.i("Register Activity", "form posted to php");
        }
        catch (Exception e)
        {
            Log.e("Fail 1", e.toString());


            statusString = "Invalid IP Address";
        }
    }
    public String getRegId(Context context, SharedPreferences prefs){
        String registrationId = prefs.getString("regId", "");
        if (registrationId.isEmpty())
        {
            Log.i("Register Activity", "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt("appVersion", Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context.getApplicationContext());
        if (registeredVersion != currentVersion)
        {
            Log.i("Register Activity", "App version changed.");
            return "";
        }
        return registrationId;
    }

}
