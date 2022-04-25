package com.sany.alphatech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after delay
                finish();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 1000);
    }
}