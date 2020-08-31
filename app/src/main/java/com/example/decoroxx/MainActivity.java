package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private  DatabaseHandler databaseHandler;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }

    public void goIn(View view){
        Intent intent = new Intent(this, DecoMainActivity.class);
        startActivity(intent);
    }

}
