package com.e.amichai.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HowToPlay extends AppCompatActivity {
    private boolean iCameFromMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

    }

    @Override
    public void onBackPressed() {
        MainActivity.currentActivity = getIntent().getStringExtra("came from");
        super.onBackPressed();
    }
    @Override
    protected void onRestart() {
        if (!MainActivity.currentActivity.equals("main")) {
            MainActivity.mediaPlayer.start();
        }
        super.onRestart();
    }
    @Override
    protected void onStop() {
        if (!MainActivity.currentActivity.equals("main") && !MainActivity.currentActivity.equals("settings")){
            MainActivity.mediaPlayer.pause();
        }
        super.onStop();
    }
}
