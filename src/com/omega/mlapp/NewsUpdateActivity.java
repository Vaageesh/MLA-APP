package com.omega.mlapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.omega.R;

/**
 * Created by vageesh on 2/13/15.
 */
public class NewsUpdateActivity extends Activity{

    private WebView browser;
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsupdate);
        browser = (WebView)findViewById(R.id.webView1);
        browser.setWebViewClient(new WebViewClient());
    }
}
