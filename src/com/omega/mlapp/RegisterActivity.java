package com.omega.mlapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.omega.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vageesh on 2/1/15.
 */
public class RegisterActivity extends Activity{
    String fullName;
    String email;
    String phoneNo;
    String address;
    String enteredDate;
    InputStream is=null;
    String result=null;
    String line=null;
    private ProgressDialog mProgress;
    int code;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        mProgress = new ProgressDialog(this);
        String titleId="Signing Up...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setCanceledOnTouchOutside(false);
        pref = getSharedPreferences("MLAPP", MODE_PRIVATE);
        editor = pref.edit();
        String getStatus = pref.getString("register", "nil");
        if (getStatus.equals("true")) {
            //Intent intent = new Intent(RegisterActivity.this,DisplayActivity.class);
            Intent intent = new Intent(RegisterActivity.this,TabActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.register);
            final EditText e_name = (EditText) findViewById(R.id.reg_fullname);
            final EditText e_mobile = (EditText) findViewById(R.id.reg_mobile);
            final EditText e_address = (EditText) findViewById(R.id.reg_addr);
            Button insertBtn = (Button) findViewById(R.id.btnRegister);

            insertBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    fullName = e_name.getText().toString();
                    phoneNo = e_mobile.getText().toString();
                    address = e_address.getText().toString();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    enteredDate = df.format(c.getTime());

                    //insert();
                    mProgress.show();
                    new MyDownloadTask().execute();
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            });


        }
    }
    public void insert()
    {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name",fullName));
        nameValuePairs.add(new BasicNameValuePair("email",email));
        nameValuePairs.add(new BasicNameValuePair("phoneNo",phoneNo));
        nameValuePairs.add(new BasicNameValuePair("address",address));
        //nameValuePairs.add(new BasicNameValuePair("enteredDate",enteredDate));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://localhost/insert.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

    }
    class MyDownloadTask extends AsyncTask<String,String,String>
    {


        protected void onPreExecute() {
            //display progress dialog.

        }
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("name",fullName));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("phoneNo",phoneNo));
            nameValuePairs.add(new BasicNameValuePair("address",address));
            nameValuePairs.add(new BasicNameValuePair("enteredDate",enteredDate));

            String statusString = "";

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                //HttpPost httppost = new HttpPost("http://10.0.2.2/insert.php");
                //HttpPost httppost = new HttpPost("http://testmachine.comli.com/phpscripts/insert.php");
                HttpPost httppost = new HttpPost("http://rsdoddamani.co.in/app/php_scripts/insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1a", "connection success ");

            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getApplicationContext(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();
                statusString = "Invalid IP Address";
                return statusString;
            }
            try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            System.out.println(result);
           // Log.i("result=",result);
            Log.e("pass 2", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result);
            code=(json_data.getInt("code"));
System.out.println("code"+code);
            if(code==1)
            {
                /*Toast.makeText(getBaseContext(), "Inserted Successfully",
                        Toast.LENGTH_SHORT).show();*/
                statusString = "Registered Successfully!!";
                return statusString;
            }
            else
            {
                /*Toast.makeText(getBaseContext(), "Sorry, Try Again",
                        Toast.LENGTH_LONG).show();*/
                statusString = "Sorry, Try Again";
                return statusString;
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
            return statusString;
        }



        protected void onPostExecute(String result) {
            // dismiss progress dialog and update ui
            mProgress.dismiss();
            Toast.makeText(getBaseContext(), result,
                    Toast.LENGTH_SHORT).show();
            /*editor.putString("register","true");
            editor.commit();
*/
            //Intent intent = new Intent(RegisterActivity.this,DisplayActivity.class);
            Intent intent = new Intent(RegisterActivity.this,TabActivity.class); //comented for notification test
            startActivity(intent);
            //displayNotification(intent);



            /*Intent intent = new Intent(RegisterActivity.this,GCMRegisterActivity.class);
            startActivity(intent);*/
            //finish();

            /*GCMRegHelper gcmRegHelper = new GCMRegHelper(getApplicationContext());
            gcmRegHelper.registerGCMActivity(getApplicationContext(),pref);

            String regId = gcmRegHelper.getRegId(getApplicationContext(),pref);
            Log.d("Reg id in RegisterActivity",regId);


            Intent i = gcmRegHelper.shareWithServerActivity(getApplicationContext(),pref);
            startActivity(i);
            finish();*/

            /*Intent intent = new Intent(RegisterActivity.this,TabActivity.class); //comented for notification test
            startActivity(intent);*/
            /*Intent i = gcmRegHelper.shareWithServerActivity(getApplicationContext(),pref);
            startActivity(i);
            finish();*/
        }

        protected void displayNotification(Intent intent){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(RegisterActivity.this, 0, intent, 0);

            Context context = getApplicationContext();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 00);
            Notification.Builder notification = new Notification.Builder(context)
                    .setContentTitle("News update from RS Doddamani")
                    .setContentText("Today's Update")
                    .setTicker("Notification!")
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.launch_icon);


            NotificationManager notificationManager = (NotificationManager) RegisterActivity.this
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notificationn = notification.getNotification();
            notificationManager.notify(1, notificationn);

        }
    }
    // @Override
/*
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
        //TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        /*loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });*/
    }

