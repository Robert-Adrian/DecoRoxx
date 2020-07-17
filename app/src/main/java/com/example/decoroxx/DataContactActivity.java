package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class DataContactActivity extends AppCompatActivity {
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.datacontact_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;


        TextView dataContactTitle = (TextView)findViewById(R.id.datacontact_txt);
        ScrollView scrollView = (ScrollView)findViewById(R.id.container_details);
        Button finallyBtn = (Button)findViewById(R.id.continua);

        ViewGroup.LayoutParams paramsTitle = (ViewGroup.LayoutParams)dataContactTitle.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
  //      ViewGroup.LayoutParams paramsContinueBtn = (ViewGroup.LayoutParams)finallyBtn.getLayoutParams();

        paramsTitle.height = heightScreen / 4;
        paramsScrollView.height = heightScreen / 2;
//        paramsContinueBtn.height = heightScreen / 4;

        dataContactTitle.setLayoutParams(paramsTitle);
        scrollView.setLayoutParams(paramsScrollView);
        finallyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataContactActivity.this, FinishComandActivity.class);
                startActivity(intent);
            }
        });
       // finallyBtn.setLayoutParams(paramsContinueBtn);

    }


}
