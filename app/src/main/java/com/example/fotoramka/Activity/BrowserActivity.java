package com.example.fotoramka.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.fotoramka.Model.WebDav;
import com.example.fotoramka.R;

public class BrowserActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout);
        WebView view  = (WebView) findViewById(R.id.browser);
        Uri url = getIntent().getData();
        view.setWebViewClient(new MyWeClient());
        view.loadUrl(url.toString());
    }


    public class MyWeClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(url.contains("access_token")){
                String token = GetToken(url);
               WebDav.SetClientSecret(token);
                BrowserActivity.this.finish();
                 Intent intent = new Intent(getApplicationContext(),FolderActivity.class);
                startActivity(intent);
            }
        }

        private String GetToken(String url){
            int start =  url.indexOf("access_token")+13;
            String token ="";
            for(int i=start; i<start+34;i++) {
              token+=url.charAt(i);
            }
            return token;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
