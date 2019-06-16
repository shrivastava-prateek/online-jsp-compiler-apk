package com.onlinejspcompiler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView mywebview;
    private ProgressBar spinner;
    private  ImageView imageLoad;
    String ShowOrHideWebViewInitialUse = "show";
    private SwipeRefreshLayout layoutRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageLoad = (ImageView) findViewById(R.id.imageLoading1);
        layoutRefresh = this.findViewById(R.id.swipeLayout);
        mywebview = (WebView) findViewById(R.id.webView);
        //spinner = (ProgressBar)findViewById(R.id.progressBar1);

        mywebview.setWebViewClient(new CustomWebViewClient());
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mywebview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        mywebview.loadUrl("https://www.onlinejspcompiler.com");
        layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("Inside on RefreshMethod", "Boom");
                //mywebview.reload();
                mywebview.loadUrl("https://www.onlinejspcompiler.com");
                mywebview.clearHistory();
                // write what happens after swipe refreshing
                layoutRefresh.setRefreshing(false); //this line hides the refresh button after completing
            }
        });
        /*mywebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });*/


    }

    @Override
    public void onBackPressed(){
        if(mywebview.canGoBack()) {
            mywebview.goBack();
        } else
        {
            super.onBackPressed();
        }
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            String ShowOrHideWebViewInitialUse = "hide";
            //spinner.setVisibility(View.GONE);
            imageLoad.setVisibility(View.GONE);
            view.setVisibility(mywebview.VISIBLE);
            super.onPageFinished(view, url);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}
