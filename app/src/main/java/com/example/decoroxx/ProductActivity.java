package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.NoSuchElementException;

public class ProductActivity extends AppCompatActivity {

    ConstraintLayout container;
    RadioButton colorPicker;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.product_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;
        Bundle bundle = getIntent().getExtras();
        ImageView view = (ImageView)findViewById(R.id.imageView3) ;
        ScrollView scrollView = (ScrollView)findViewById(R.id.text_description);
        View price_chart = (View)findViewById(R.id.price_layout);
        TextView title = (TextView)findViewById(R.id.title);
        TextView description = (TextView)findViewById(R.id.description);
        TextView price = (TextView)findViewById(R.id.price);
        RadioButton color_picker = (RadioButton)findViewById(R.id.color_picker);
        if (bundle != null) {
            int resId = bundle.getInt("resId");
            view.setImageResource(resId);
            title.setText(bundle.getString("title"));
            description.setText(bundle.getString("description"));
            price.setText("Pret: " + bundle.getString("price") + " Lei");
            ViewGroup.LayoutParams paramsPrice = (ViewGroup.LayoutParams)price.getLayoutParams();
            ViewGroup.LayoutParams paramsColorPicker = (ViewGroup.LayoutParams)color_picker.getLayoutParams();

            paramsPrice.width = widthScreen / 2 + widthScreen / 8;
            paramsColorPicker.width = widthScreen / 4 + widthScreen / 8;
            price.setLayoutParams(paramsPrice);
            color_picker.setLayoutParams(paramsColorPicker);

        }
        ViewGroup.LayoutParams paramsImageView = (ViewGroup.LayoutParams)view.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
        ViewGroup.LayoutParams paramsPriceChart = (ViewGroup.LayoutParams)price_chart.getLayoutParams();

        int heightPhoto = ((heightScreen * 3) / 8);
        paramsImageView.height = ((heightScreen * 3) / 8);
        paramsImageView.width = widthScreen;
        view.setLayoutParams(paramsImageView);


        paramsScrollView.height = ((heightScreen * 3)/ 4);
        paramsScrollView.width = widthScreen;
        scrollView.setLayoutParams(paramsScrollView);


        paramsPriceChart.height = heightScreen / 4;
        paramsPriceChart.width = widthScreen;
        price_chart.setLayoutParams(paramsPriceChart);


        colorPicker = (RadioButton)findViewById(R.id.color_picker);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product();
                String[] colorsVector = {"#e60923", "#fcba03"};
                System.out.println(colorsVector);
                alertDialog(colorsVector);
            }
        });

    }

    public void alertDialog(final String[] colors) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Selecteaza o culoare");
        View checkBoxView = View.inflate(this, R.layout.checkbox, null);
        final RadioGroup containerCulori = (RadioGroup)checkBoxView.findViewById(R.id.container_culori);
        for (int i = 0; i < colors.length; i++) {
            final RadioButton checkBox = new RadioButton(containerCulori.getContext());

            checkBox.setBackgroundColor(Color.parseColor(colors[i]));

            checkBox.setPadding(20,0,0,0);
            checkBox.setButtonDrawable(R.drawable.custom_checkbox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {

                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{-android.R.attr.state_enabled},
                                    new int[]{android.R.attr.state_enabled}
                            },
                            new int[]{
                                    Color.BLUE,
                                    ((ColorDrawable)v.getBackground()).getColor()
                            }
                    );

                    colorPicker.setButtonTintList(colorStateList);
                    colorPicker.invalidate();
                }
            });
            containerCulori.addView(checkBox);
        }
        dialog.setView(checkBoxView);



      /*  dialog.setPositiveButton("Gata",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        dialog.setNegativeButton("Anulare",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });*/
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

}
