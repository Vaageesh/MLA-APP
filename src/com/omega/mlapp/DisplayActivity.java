package com.omega.mlapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.omega.R;

/**
 * Created by vageesh on 2/8/15.
 */
public class DisplayActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        // Set View to display.xml
        setContentView(R.layout.display);


        Button upd=(Button)findViewById(R.id.updatesbtn);
        upd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Uri uri = Uri.parse("https://www.google.co.in");
                //Intent newsupdateLink = new Intent(Intent.ACTION_VIEW,uri);
                Intent intent = new Intent(context,NewsActivity.class);
               // newsupdateLink.setData(Uri.parse("https://www.google.co.in"));
                startActivity(intent);




            }
        });

        Button biogr=(Button)findViewById(R.id.biographybtn);
        biogr.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent biographyLink = new Intent(android.content.Intent.ACTION_VIEW);
                //biographyLink.setData(Uri.parse("https://www.google.co.in"));
                /*biographyLink.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                biographyLink.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/

//                biographyLink.setData(Uri.parse("https://www.google.co.in"));
                Intent biographyLink = new Intent(context,BiohraphActivity.class);
                startActivity(biographyLink);

            }
        });

        Button cntus=(Button)findViewById(R.id.contactbtn);
        cntus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent contactusLink = new Intent(android.content.Intent.ACTION_VIEW);
                //contactusLink.setData(Uri.parse("https://www.google.co.in"));
                Intent contactusLink = new Intent(context,ContactActivity.class);
                startActivity(contactusLink);

            }
        });

        Button aboutBtn=(Button)findViewById(R.id.aboutBtn);
        aboutBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent contactusLink = new Intent(android.content.Intent.ACTION_VIEW);
                //contactusLink.setData(Uri.parse("https://www.google.co.in"));
                Intent abtusLink = new Intent(context,AboutUsActivity.class);
                startActivity(abtusLink);

            }
        });
    }
    /*public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }*/
}
