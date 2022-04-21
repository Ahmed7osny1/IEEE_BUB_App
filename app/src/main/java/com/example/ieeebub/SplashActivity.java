package com.example.ieeebub;


import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow() ;
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        ImageView backgroundImage = findViewById(R.id.SplashScreenImage1);
        Animation topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_left);
        backgroundImage.startAnimation(topAnimation);

        TextView backgroundImage2 = findViewById(R.id.SplashScreenImage2);
        Animation topAnimation2 = AnimationUtils.loadAnimation(this, R.anim.end_right);
        backgroundImage2.startAnimation(topAnimation2);


        Thread splashTread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3500);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                super.run();
            }
        };
        splashTread.start();
    }
}