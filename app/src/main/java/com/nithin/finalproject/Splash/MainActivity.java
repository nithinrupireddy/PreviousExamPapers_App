package com.nithin.finalproject.Splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nithin.finalproject.Login.LoginActivity;
import com.nithin.finalproject.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent homeintent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(homeintent);
                finish();
            }}, SPLASH_TIME_OUT);

    }
}
