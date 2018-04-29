package com.example.dell.elixir.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dell.elixir.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                Intent i = new Intent(getBaseContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
        };

        myThread.start();


    }
}
