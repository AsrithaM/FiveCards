package com.apps.asritha.fivecards;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home_page);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void play(View v) {
        Intent i = new Intent(HomePage.this, MainActivity.class);
        startActivity(i);
    }

    public void rules(View v) {
        Intent i = new Intent(HomePage.this, Instructions.class);
        startActivity(i);
    }
}
