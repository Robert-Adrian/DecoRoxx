package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShopActivity extends AppCompatActivity {
    Button quantity;
    TableLayout list_item_shop;
    static DatabaseHandler databaseHandler;
    View bottom_component;
    int totalSum = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.shop_activity);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseHandler = new DatabaseHandler(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        int widthScreen = displayMetrics.widthPixels;

        TextView view = (TextView)findViewById(R.id.cos_txt) ;
        ScrollView scrollView = (ScrollView)findViewById(R.id.container_shop);
        bottom_component = (View)findViewById(R.id.bottom_component);

        ViewGroup.LayoutParams paramsTextView = (ViewGroup.LayoutParams)view.getLayoutParams();
        ViewGroup.LayoutParams paramsScrollView = (ViewGroup.LayoutParams)scrollView.getLayoutParams();
        ViewGroup.LayoutParams paramsBottomComponent = (ViewGroup.LayoutParams)bottom_component.getLayoutParams();

        list_item_shop = (TableLayout)scrollView.findViewById(R.id.list_item_shop);
        final List<Product> productList = databaseHandler.getProductShop();

        for (int i = 0; i < productList.size(); i++) {
            final TableRow tableRow = new TableRow(list_item_shop.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            tableRow.setLayoutParams(lp);

            View item_shop = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_shop, null);
            View details_shop_element = (View)item_shop.findViewById(R.id.details_shop_element);
            ImageView photoShopElement = (ImageView)item_shop.findViewById(R.id.photo);
            final View quantity_price_item_shop = (View)details_shop_element.findViewById(R.id.quantity_price_shop);
            final RadioButton colorPicker = (RadioButton) details_shop_element.findViewById(R.id.textView4);

            photoShopElement.setImageBitmap(BitmapFactory.decodeByteArray(productList.get(i).getImageProduct(), 0, productList.get(i).getImageProduct().length));
            int textLength = colorPicker.getText().toString().length() - 3;
            colorPicker.setText(productList.get(i).getProductDescription().substring(0, textLength) + "...");

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[]{
                            Color.parseColor(productList.get(i).getSelectedColor())
                    }
            );
            colorPicker.setButtonTintList(colorStateList);

            final int finalI = i;

            colorPicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] colorsVector = productList.get(finalI).getColorList().split(";");
                    alertDialogColorPicker(productList.get(finalI), colorsVector, colorPicker);
                }
            });

            colorPicker.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    alertDialogDelElem(productList.get(finalI), tableRow);
                    return false;
                }
            });

            quantity = (Button)quantity_price_item_shop.findViewById(R.id.cantitate);
            quantity.setText(productList.get(i).getQuantity() + " buc.");
            totalSum += productList.get(i).getQuantity() * productList.get(i).getPrice();

            Button price = (Button)quantity_price_item_shop.findViewById(R.id.pret);
            price.setText(Integer.parseInt(((Button)quantity).getText().toString().substring(0, ((Button)quantity).getText().toString().indexOf("buc.")).trim()) * productList.get(finalI).getPrice() + " Lei");

            quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button price = (Button)quantity_price_item_shop.findViewById(R.id.pret);
                    alertDialogAddCan(productList.get(finalI), v, price);
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
            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    alertDialogDelElem(productList.get(finalI), (TableRow) v);
                    return false;
                }
            });
            list_item_shop.addView(tableRow, i);
        }

        View total_price = bottom_component.findViewById(R.id.shop_total_price);
        TextView total = total_price.findViewById(R.id.total_price);
        total.setText(totalSum + " Lei");

        Button continueBtn = (Button)bottom_component.findViewById(R.id.continua);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list_item_shop.getChildCount() != 1) {
                    Intent intent = new Intent(ShopActivity.this, DataContactActivity.class);
                    intent.putExtra("totalComanda", totalSum);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Cosul este gol !", Toast.LENGTH_SHORT).show();
                }
            }
        });


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

    public void alertDialogAddCan(final Product product, final View quantity, final View price) {
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
                            ((Button)quantity).setText(number + " buc.");
                            int oldPrice = Integer.parseInt(((Button) price).getText().toString().substring(0, ((Button) price).getText().toString().indexOf("Lei")).trim());
                            totalSum -= oldPrice;
                            ((Button)price).setText(number * product.getPrice() + " Lei");
                            totalSum += Integer.parseInt(((Button) price).getText().toString().substring(0, ((Button) price).getText().toString().indexOf("Lei")).trim());

                            View total_price = bottom_component.findViewById(R.id.shop_total_price);
                            TextView total = total_price.findViewById(R.id.total_price);
                            total.setText(totalSum + " Lei");

                            databaseHandler.updateQuantity(product, number);
                        }

                    }
                });
        dialog.setNegativeButton("Anulare",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void alertDialogDelElem(final Product product, final TableRow tableRow) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Vrei sa stergi buchetul ?");

        dialog.setPositiveButton("Da",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Button price = tableRow.findViewById(R.id.pret);
                        Button quantity = tableRow.findViewById(R.id.cantitate);
                        totalSum -= Integer.parseInt(((Button) price).getText().toString().substring(0, ((Button) price).getText().toString().indexOf("Lei")).trim());//Integer.parseInt(((Button) price).getText().toString().substring(0, price.getText().toString().indexOf("Lei")).trim()) * Integer.parseInt(((Button) quantity).getText().toString().substring(0, quantity.getText().toString().indexOf("buc")).trim());
                        View total_price = bottom_component.findViewById(R.id.shop_total_price);
                        TextView total = total_price.findViewById(R.id.total_price);
                        total.setText(totalSum + " Lei");
                        list_item_shop.removeView(tableRow);
                        databaseHandler.deleteRow(product);
                    }
                });
        dialog.setNegativeButton("Nu",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void alertDialogColorPicker(final Product product, final String[] colors, final RadioButton colorPicker) {
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
                                    new int[]{android.R.attr.state_enabled}
                            },
                            new int[]{
                                    ((ColorDrawable)v.getBackground()).getColor()
                            }
                    );
                    product.setSelectedColor(String.format("#%06X", (0xFFFFFF & ((ColorDrawable) v.getBackground()).getColor())));
                    colorPicker.setButtonTintList(colorStateList);
                    colorPicker.invalidate();
                }
            });
            containerCulori.addView(checkBox);
        }
        dialog.setView(checkBoxView);

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }


}
