package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {
    Button quantity;
    TableLayout list_item_shop;
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
        list_item_shop = (TableLayout)scrollView.findViewById(R.id.list_item_shop);
        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(list_item_shop.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            tableRow.setLayoutParams(lp);

            View item_shop = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_shop, null);
            View details_shop_element = (View)item_shop.findViewById(R.id.details_shop_element);
            ImageView photoShopElement = (ImageView)item_shop.findViewById(R.id.photo);
            View quantity_price_item_shop = (View)details_shop_element.findViewById(R.id.quantity_price_shop);

            final int finalI = i;
            item_shop.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    alertDialogDelElem(v, finalI);
                    return false;
                }
            });
            Button continueBtn = (Button)bottom_component.findViewById(R.id.continua);
            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShopActivity.this, DataContactActivity.class);
                    startActivity(intent);
                }
            });
            quantity = (Button)quantity_price_item_shop.findViewById(R.id.cantitate);
            quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogAddCan(v);
                }
            });
            ViewGroup.LayoutParams paramsDetailsShop = (ViewGroup.LayoutParams) details_shop_element.getLayoutParams();
            ViewGroup.LayoutParams paramsPhtoto = (ViewGroup.LayoutParams) photoShopElement.getLayoutParams();

            paramsDetailsShop.width = widthScreen - (((widthScreen) * 2) / 5);
            paramsPhtoto.width = ((widthScreen) * 2) / 5;
            paramsPhtoto.height = (paramsDetailsShop.height ) / 2;

            details_shop_element.setLayoutParams(paramsDetailsShop);
            photoShopElement.setLayoutParams(paramsPhtoto);

            tableRow.addView(item_shop);
            list_item_shop.addView(tableRow, i);
        }

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

    public void alertDialogAddCan(final View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Introdu cantitatea pe care o doresti");

        final EditText quantityInsert = new EditText(dialog.getContext());
        quantityInsert.setHint("Cantitate");
        dialog.setView(quantityInsert);

        dialog.setPositiveButton("Gata",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (!quantityInsert.getText().toString().isEmpty()) {
                            int number = Integer.parseInt(quantityInsert.getText().toString());
                            ((Button)v).setText(number + " buc.");
                        }

                    }
                });
        dialog.setNegativeButton("Anulare",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void alertDialogDelElem(final View v, final int index) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Vrei sa stergi buchetul ?");

        dialog.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        list_item_shop.removeViewAt(index);
                    }
                });
        dialog.setNegativeButton("Nu",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }


}
