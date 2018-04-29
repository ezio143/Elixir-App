package com.example.dell.elixir.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.dell.elixir.R;
import com.example.dell.elixir.Ubidots_sync.OurViewClient;

import org.w3c.dom.Text;


public class Ubidots_Activity extends AppCompatActivity {


    int choice = 0;
    private WebView mWebView;
    private String phUrl = "https://app.ubidots.com/ubi/getchart/6hmXJNKuNlSNPI6n9MCZhcgSbek";
    private String TempUrl = "https://app.ubidots.com/ubi/getchart/OYbkDHEeNfo2ytta3zng04i54vc";
    private String TurbUrl = "https://app.ubidots.com/ubi/getchart/Z0S03LTRKyuA4LUg1aDPWUe9r8M";
    ProgressDialog dialog;
    private TextView txt_yAxis;


public Ubidots_Activity(){
    choice = HomeActivity.choice;
}


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubidots_);
        setTitle("Graph");


        dialog = new ProgressDialog(Ubidots_Activity.this);
            txt_yAxis = (TextView) findViewById(R.id.txt_yaxis);
        mWebView = (WebView) findViewById(R.id.webview_chart);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new OurViewClient());
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    dialog.dismiss();
                }
                else{
                    dialog.setTitle("Chart Values");
                    dialog.setMessage("Loading...");
                    dialog.show();
                }
            }
        });


        setTitle("Elixir");

        switch (choice) {

            case 1:mWebView.loadUrl(TempUrl);
            txt_yAxis.setText("Y-Axis : Temperature");
                //FragmentTransaction tra = getSupportFragmentManager().beginTransaction();
            //tra.add(R.id.frgmt, new Temp_ChartFragment());
            //tra.commit();
            break;

            case 0:mWebView.loadUrl(phUrl);
            txt_yAxis.setText("Y-Axis : pH");
           //     FragmentTransaction tra2 = getSupportFragmentManager().beginTransaction();
            //    tra2.add(R.id.frgmt,new ph_chart_fragment());
            //    tra2.commit();
                break;

            case 2:
                mWebView.loadUrl(TurbUrl);
                txt_yAxis.setText("Y-Axis : Turbidity");
            //    FragmentTransaction tra3 = getSupportFragmentManager().beginTransaction();
            //    tra3.add(R.id.frgmt,new turb_chart_Fragment());
            //    tra3.commit();
                break;
        }

    }
}
