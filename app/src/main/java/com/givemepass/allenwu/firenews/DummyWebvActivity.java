package com.givemepass.allenwu.firenews;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DummyWebvActivity extends AppCompatActivity {
private WebView webView;
    ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_webv);

        initActionbar();
        webView= (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        if(getIntent()!=null){
            String netURL=getIntent().getStringExtra("key");
        webView.loadUrl(netURL);
        }
    }

    private void initActionbar() {
         actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Dummy Web");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){

            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){

            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            setProgressBarVisibility(true);

            actionbar.setTitle("正在 Loading...");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            actionbar.setTitle("Dummy Web");
        }
    }
}
