package com.omega.mlapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MyActivity extends Activity
{
    String fullName;
    String email;
    String phoneNo;
    String address;
    String enteredDate;
    InputStream is=null;
    String result=null;
    String line=null;
    int code;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

        final EditText e_name =(EditText) findViewById(R.id.reg_fullname);
        final EditText e_mobile = (EditText) findViewById(R.id.reg_mobile);
        final EditText e_address = (EditText) findViewById(R.id.reg_addr);
        Button insertBtn = (Button)findViewById(R.id.btnRegister);

        insertBtn.setOnClickListener(new OnClickListener() {
            public void onClick (View v){

                fullName = e_name.getText().toString();
                phoneNo = e_mobile.getText().toString();
                address = e_address.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                enteredDate = df.format(c.getTime());

               // insert();
                new MyDownloadTask().execute();
            }
        });

        // Listening to register new account link
//        registerScreen.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // Switching to Register screen
//                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
//                startActivity(i);
//            }
//        });
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
            HttpPost httppost = new HttpPost("http://10.0.2.2/insert.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
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

//        try
//        {
//            BufferedReader reader = new BufferedReader
//                    (new InputStreamReader(is,"iso-8859-1"),8);
//            StringBuilder sb = new StringBuilder();
//            while ((line = reader.readLine()) != null)
//            {
//                sb.append(line + "\n");
//            }
//            is.close();
//            result = sb.toString();
//            Log.e("pass 2", "connection success ");
//        }
//        catch(Exception e)
//        {
//            Log.e("Fail 2", e.toString());
//        }
//
//        try
//        {
//            JSONObject json_data = new JSONObject(result);
//            code=(json_data.getInt("code"));
//
//            if(code==1)
//            {
//                Toast.makeText(getBaseContext(), "Inserted Successfully",
//                        Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(getBaseContext(), "Sorry, Try Again",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//        catch(Exception e)
//        {
//            Log.e("Fail 3", e.toString());
//        }
    }
    class MyDownloadTask extends AsyncTask<Void,Void,Void>
    {


        protected void onPreExecute() {
            //display progress dialog.

        }
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("name",fullName));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("phoneNo",phoneNo));
            nameValuePairs.add(new BasicNameValuePair("address",address));
            //nameValuePairs.add(new BasicNameValuePair("enteredDate",enteredDate));

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://10.0.2.2/insert.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                //is = entity.getContent();
                Log.e("pass 1a", "connection success ");
            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
                Toast.makeText(getApplicationContext(), "Invalid IP Address",
                        Toast.LENGTH_LONG).show();
            }
            /*try
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
                Toast.makeText(getBaseContext(), "Inserted Successfully",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Sorry, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }*/
            return null;
        }



        protected void onPostExecute(Void result) {
            // dismiss progress dialog and update ui
        }
    }
   // @Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
}
