package com.example.samawia;

import android.content.Intent;
import android.os.Bundle;;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


            ImageView imageView = findViewById(R.id.splashImage);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_animation);
            imageView.startAnimation(animation);
            imageView.setVisibility(View.VISIBLE);



            // Set a listener to start the next activity after animation ends
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Start the next activity after the animation ends
                    Intent intent = new Intent(MainActivity.this, Signup.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to it on pressing back button
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }
    }