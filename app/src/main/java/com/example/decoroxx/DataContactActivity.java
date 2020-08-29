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
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DataContactActivity extends AppCompatActivity {
    static DatabaseHandler databaseHandler;
    static EditText nameField;
    static EditText phoneField;
    static EditText addressField;
    static ScrollView scrollView;
    static EditText[] informationFields;
    int totalSuma;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.datacontact_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        databaseHandler = new DatabaseHandler(this);
        totalSuma = (int) getIntent().getSerializableExtra("totalComanda");
        scrollView = (ScrollView)findViewById(R.id.container_details);
        nameField = (EditText) scrollView.findViewById(R.id.name_field);
        phoneField = (EditText) scrollView.findViewById(R.id.phone_field);
        addressField = (EditText) scrollView.findViewById(R.id.address_field);
        informationFields = new EditText[]{nameField, phoneField, addressField};
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;

        TextView dataContactTitle = (TextView)findViewById(R.id.datacontact_txt);

        ViewGroup.LayoutParams paramsTitle = (ViewGroup.LayoutParams)dataContactTitle.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
  //      ViewGroup.LayoutParams paramsContinueBtn = (ViewGroup.LayoutParams)finallyBtn.getLayoutParams();

        paramsTitle.height = heightScreen / 4;
        paramsScrollView.height = heightScreen / 2;
//        paramsContinueBtn.height = heightScreen / 4;

        dataContactTitle.setLayoutParams(paramsTitle);
        scrollView.setLayoutParams(paramsScrollView);

    }

    public void finalization(View view) {
        int state = 0;
        for (int i = 0; i < 3; i++) {
            if (informationFields[i].getText().toString().isEmpty()) {
                state = 1;
            }
        }
        if (state == 0) {
            Intent intent = new Intent(this, FinishComandActivity.class);
            String[] nameTag = new String[]{"nameField", "phoneField", "addressField"};
            intent.putExtra("totalComanda", totalSuma);
            for (int i = 0; i < 3; i++) {
                intent.putExtra(nameTag[i], informationFields[i].getText().toString());
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "Toate campurile sunt obligatorii !", Toast.LENGTH_SHORT).show();

        }
    }

}
