package com.example.realtimechat.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realtimechat.MainActivity;
import com.example.realtimechat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WaitingScreenActivity extends AppCompatActivity {
    ImageView view;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_screen);
        view = findViewById(R.id.logo);
        animation = AnimationUtils.loadAnimation(WaitingScreenActivity.this, R.anim.anim_intent_in_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
                view.startAnimation(animation);
            }
        }, 2500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }, 5500);
    }

}