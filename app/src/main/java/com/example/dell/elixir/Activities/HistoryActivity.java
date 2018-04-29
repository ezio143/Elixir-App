package com.example.dell.elixir.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.dell.elixir.R;
import com.example.dell.elixir.Ubidots_sync.OurViewClient;

public class HistoryActivity extends AppCompatActivity {


    private ProgressDialog dialog;

    private String TempUrl = "https://app.ubidots.com/ubi/getchart/vjOGlAdlnDjEQ5losIBnB8jG9n4";
    private String TurbUrl = "https://app.ubidots.com/ubi/getchart/fincSWFQg65SYDa6wjZS3_PgMKs";
    private String pHUrl = "https://app.ubidots.com/ubi/getchart/vqhhzpx3lz2d7XNYowa-fo45U6U";


    WebView view_temp,view_turb,view_ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("History");
        view_temp = (WebView) findViewById(R.id.webview_temp);
        view_turb = (WebView) findViewById(R.id.webview_turb);
        view_ph = (WebView) findViewById(R.id.webview_ph);
        dialog = new ProgressDialog(HistoryActivity.this);

        // Initialize WebView and enable JavaScript
        view_temp.getSettings().setJavaScriptEnabled(true);
        view_temp.setWebViewClient(new OurViewClient());
        view_turb.getSettings().setJavaScriptEnabled(true);
        view_turb.setWebViewClient(new OurViewClient());
        view_ph.getSettings().setJavaScriptEnabled(true);
        view_ph.setWebViewClient(new OurViewClient());
        view_temp.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    dialog.dismiss();
                }
                else{
                    dialog.setTitle("Table Values");
                    dialog.setMessage("Loading...");
                    dialog.show();
                }
            }
        });

        // Navigate to site
        view_temp.loadUrl(TempUrl);
        view_turb.loadUrl(TurbUrl);
        view_ph.loadUrl(pHUrl);

    }
}
