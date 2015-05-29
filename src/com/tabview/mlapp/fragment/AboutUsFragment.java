package com.tabview.mlapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.omega.R;


/**
 * Created by vageesh on 3/14/15.
 */
public class AboutUsFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.newsupdate, container, false);
        String url = "http://app.rsdoddamani.co.in/about.html";
        //String url = "http://www.google.com";
        //WebView loadWeb = new WebView();
        //   browser = ((WebView)(getView().findViewById(R.id.webView1)));
        WebView webview = (WebView) rootView.findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new webClient());
        webview.loadUrl(url);
        return rootView;

        //setContentView(R.layout.newsupdate);
        //browser = (WebView)findViewById(R.id.webView1);
        //browser.setWebViewClient(new WebViewClient());
    }

    public void loadUrl(String url) {
        ((WebView)(getView().findViewById(R.id.webView1))).loadUrl(url);
    }
    private class webClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;

        }

    }

}
