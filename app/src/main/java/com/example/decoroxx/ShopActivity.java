package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.shop_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;
        // System.out.println(height + " " + width);
        TextView view = (TextView)findViewById(R.id.cos_txt) ;
        ScrollView scrollView = (ScrollView)findViewById(R.id.container_shop);
        View bottom_component = (View)findViewById(R.id.bottom_component);

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


    }
}
