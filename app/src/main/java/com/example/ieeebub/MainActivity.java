package com.example.ieeebub;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow() ;
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();

        //SETTINGS
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabaseEnabled(true);
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);

        webSettings.setDatabasePath("/data/data/"+getPackageName()+"/databases/");
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());

        if (Build.VERSION.SDK_INT >= 26) {
            mWebView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, true);
        }

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {

            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // WEBSITE URL
        mWebView.loadUrl("https://www.ieee-bub.com/");

        mWebView.setWebViewClient(new UriLoading() {
            @Override

            public void onPageFinished(WebView view, String url) {
                if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
                findViewById(R.id.webview).setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= 21) flushCookies();
                else CookieSyncManager.getInstance().sync();
            }

        });

        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else
        {
        }
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {
        mWebView.loadUrl("about:blank");
        mWebView.stopLoading();
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please turn on your WIFI or Mobile DATA and try again!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mWebView.loadUrl("about:blank");
                mWebView.stopLoading();
                finish();
                startActivity(getIntent());

            }
        });

        return builder;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
            else
            {
            }
        } else {
            super.onBackPressed();
        }
    }
    @TargetApi(21)
    private void flushCookies() {

        CookieManager.getInstance().flush();
    }
    @Override
    public void onStart() {
        super.onStart();

    }


}