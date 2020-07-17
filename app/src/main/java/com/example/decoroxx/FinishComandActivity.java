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

public class FinishComandActivity extends AppCompatActivity {
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.finish_comand_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;

        TextView view = (TextView)findViewById(R.id.finish_txt);
        ScrollView scrollView = (ScrollView)findViewById(R.id.container_finish);
        View bottom_component = (View)findViewById(R.id.bottom_component_finish);
        ViewGroup.LayoutParams paramsTextView = (ViewGroup.LayoutParams)view.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
        ViewGroup.LayoutParams paramsBottomComponent = (ViewGroup.LayoutParams)bottom_component.getLayoutParams();

        paramsBottomComponent.height = (heightScreen / 4);
        paramsBottomComponent.width = widthScreen;
        bottom_component.setLayoutParams(paramsBottomComponent);

        heightScreen = heightScreen - (heightScreen / 4);

        paramsScrollView.height = (heightScreen * 3) / 4;
        paramsScrollView.width = widthScreen;
        scrollView.setLayoutParams(paramsScrollView);

        heightScreen = heightScreen - ((heightScreen * 3) / 4);

        paramsTextView.height = heightScreen;
        paramsTextView.width = widthScreen;
        view.setLayoutParams(paramsTextView);

        Button sendBtn = (Button)bottom_component.findViewById(R.id.continua);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishComandActivity.this, ThankYouActivity.class);
                startActivity(intent);
            }
        });
    }

}
