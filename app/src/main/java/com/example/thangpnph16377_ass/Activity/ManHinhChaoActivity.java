package com.example.thangpnph16377_ass.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.thangpnph16377_ass.R;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app's main activity
                Intent i = new Intent(ManHinhChaoActivity.this, DangNhapActivity.class);
                startActivity(i);

                // Close this activity
                finish();
            }
        }, 2000);
    }
}