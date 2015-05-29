package com.omega.mlapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.omega.R;
import com.omega.notification.GCMRegisterActivity;
import com.tabView.adapter.FragmentAdapter;

/**
 * Created by vageesh on 3/14/15.
 */
public class TabActivity extends FragmentActivity implements ActionBar.TabListener{
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private ActionBar actionBar;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private String[] tabs = {"News/Update","About Us","Biography","Contact"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabview);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        pref = getSharedPreferences("MLAPP", MODE_PRIVATE);
        String getStatus = pref.getString("register", "nil");
        editor = pref.edit();



        viewPager.setAdapter(fragmentAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        /** Defining a listener for pageChange */
        ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actionBar.setSelectedNavigationItem(position);
            }
        };

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * on swipe select the respective tab
             */
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        if(getStatus.equals("true")){
            Log.i("Tab act","already registerd");
            //new RegTask().execute(null,null,null);
        }else{
            Log.i("Tab Act","not registerd");

            editor.putString("register","true");
            editor.commit();
            new RegTask().execute(null,null,null);

        }


    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //ft.detach(ft);
    }

    class RegTask extends AsyncTask<String,String,String>
    {


        protected void onPreExecute() {
            //display progress dialog.

        }
        protected String doInBackground(String... params) {
            Intent intent = new Intent(TabActivity.this,GCMRegisterActivity.class);
            startActivity(intent);
            //finish();
            return null;
        }

        protected void onPostExecute(String result) {

        }


    }

}
