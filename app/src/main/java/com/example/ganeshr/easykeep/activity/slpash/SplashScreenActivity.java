package com.example.ganeshr.easykeep.activity.slpash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.ganeshr.easykeep.R;
import com.example.ganeshr.easykeep.activity.HomeActivity;

import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                finish();
            }
        }, 2500);
    }

}
