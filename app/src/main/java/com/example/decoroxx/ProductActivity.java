package com.example.decoroxx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;

public class ProductActivity extends AppCompatActivity {

    ConstraintLayout container;
    RadioButton colorPicker;


    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.product_activity);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;
        // System.out.println(height + " " + width);
        ImageView view = (ImageView)findViewById(R.id.imageView3) ;
        ScrollView scrollView = (ScrollView)findViewById(R.id.text_description);
        View price_chart = (View)findViewById(R.id.price_layout);

        ViewGroup.LayoutParams paramsImageView = (ViewGroup.LayoutParams)view.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
        ViewGroup.LayoutParams paramsPriceChart = (ViewGroup.LayoutParams)price_chart.getLayoutParams();

        paramsScrollView.height = (heightScreen * 3) / 4;
        paramsScrollView.width = widthScreen;
        scrollView.setLayoutParams(paramsScrollView);

        paramsImageView.height = heightScreen / 3;
        paramsImageView.width = widthScreen;
        view.setLayoutParams(paramsImageView);

        paramsPriceChart.height = (heightScreen / 4);
        paramsPriceChart.width = widthScreen;
        price_chart.setLayoutParams(paramsPriceChart);


        colorPicker = (RadioButton)findViewById(R.id.color_picker);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] colorsVector = getResources().getIntArray(R.array.colors);

            }
        });

    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

}
