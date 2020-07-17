package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThankYouActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.thank_you_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;
        View sendTitle = (View)findViewById(R.id.title_send);
        TextView message1 = (TextView)findViewById(R.id.message1);
        TextView message2 = (TextView)findViewById(R.id.message2);
        View containerFlower = (View)findViewById(R.id.container_flower_logo);
        ImageView flowerLogo = (ImageView)containerFlower.findViewById(R.id.imageView5);

        ConstraintLayout.LayoutParams paramsFlowerLogo = (ConstraintLayout.LayoutParams)flowerLogo.getLayoutParams();
        paramsFlowerLogo.width = widthScreen;
        paramsFlowerLogo.height = heightScreen / 2;
        paramsFlowerLogo.setMargins(0,0,widthScreen / 2, 0);
        ViewGroup.LayoutParams paramsSendTitle = (ViewGroup.LayoutParams)sendTitle.getLayoutParams();
        ViewGroup.LayoutParams paramsContainerFlower = (ViewGroup.LayoutParams)containerFlower.getLayoutParams();
        //ViewGroup.LayoutParams paramsMessage1 = (ViewGroup.LayoutParams)message1.getLayoutParams();
        //ViewGroup.LayoutParams paramsMessage2 = (ViewGroup.LayoutParams)message2.getLayoutParams();

        paramsSendTitle.height = heightScreen / 8;
        paramsContainerFlower.height = heightScreen / 2;
        sendTitle.setLayoutParams(paramsSendTitle);
        flowerLogo.setLayoutParams(paramsFlowerLogo);
        containerFlower.setLayoutParams(paramsContainerFlower);

        Button home = (Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankYouActivity.this, DecoMainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        return;
    }

}
