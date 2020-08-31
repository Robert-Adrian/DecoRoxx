package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DecoMainActivity extends AppCompatActivity {
    Menu myMenu;
    MenuItem selectedItem;
    static DatabaseHandler databaseHandler;
    List<MenuItem> listItems;
    ScrollView scrollView;
    TableLayout tableProducts;
    private InterstitialAd mInterstitialAd;
    private int appears;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deco_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.createDataBase();
        try {
            databaseHandler.copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//"ca-app-pub-8197630461157665/4558639680");
        appears = 1;

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_bar, menu);
                MenuItem selectedItem = (MenuItem)menu.findItem(R.id.buchets);

                return true;
            }
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                myMenu = ((BottomNavigationView)findViewById(R.id.bottomNavigationView)).getMenu();
                listItems = new ArrayList<>();
                listItems.add((MenuItem)myMenu.findItem(R.id.buchets));
                listItems.add((MenuItem)myMenu.findItem(R.id.events));
                listItems.add((MenuItem)myMenu.findItem(R.id.mart));
                listItems.add((MenuItem)myMenu.findItem(R.id.shop));

                Intent intent;
                switch(menuItem.getItemId()) {
                    case R.id.buchets:
                        final List< Product> Products =  databaseHandler.getProducts("buchet");
                        menuItem.setIcon(R.drawable.item1_filled);
                        listItems.get(1).setIcon(R.drawable.item2);
                        listItems.get(2).setIcon(R.drawable.item3);
                        listItems.get(3).setIcon(R.drawable.item4);

                        menuItem.setChecked(true);
                        menuItem.setCheckable(true);
                        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView2);
                        TableLayout tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
                        tableProducts.removeAllViews();
                        int linie = 0;
                        for (int i = 0; i < Products.size(); i++) {
                            View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                            ImageView imageView = (ImageView)view.findViewById(R.id.imageCard);
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(Products.get(i).getImageProduct(), 0, Products.get(i).getImageProduct().length));

                            TextView description = (TextView)view.findViewById(R.id.descriptionCard);
                            description.setText(Products.get(i).getProductTitle().trim() + "\n");


                            TextView price = (TextView)view.findViewById(R.id.priceCard);
                            price.setText((int) Products.get(i).getPrice() + " lei");


                            TableRow rowProducts = new TableRow(tableProducts.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                            rowProducts.setLayoutParams(lp);
                            final int finalI = i;
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                    intent.putExtra("id", Products.get(finalI).getIdProduct());
                                    intent.putExtra("type", Products.get(finalI).getType());
                                    intent.putExtra("image", Products.get(finalI).getImageProduct());
                                    intent.putExtra("title", Products.get(finalI).getProductTitle().trim());
                                    intent.putExtra("description", Products.get(finalI).getProductDescription());
                                    intent.putExtra("price", Products.get(finalI).getPrice());
                                    intent.putExtra("colors", Products.get(finalI).getColorList().trim());

                                    startActivity(intent);
                                }
                            });
                            rowProducts.addView(view);
                            if (i + 1 < Products.size()) {
                                View child2 = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col2, null);;
                                ImageView imageView2 = (ImageView)child2.findViewById(R.id.imageView2);
                                imageView2.setImageBitmap(BitmapFactory.decodeByteArray(Products.get(i + 1).getImageProduct(), 0, Products.get(i + 1).getImageProduct().length));


                                TextView description2 = (TextView)child2.findViewById(R.id.textView2);
                                description2.setText(Products.get(i + 1).getProductTitle().trim() + "\n");


                                TextView price2 = (TextView)child2.findViewById(R.id.textView3);
                                price2.setText(((int) Products.get(i + 1).getPrice()) + " lei");
                                child2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                        intent.putExtra("id", Products.get(finalI + 1).getIdProduct());
                                        intent.putExtra("type", Products.get(finalI + 1).getType());
                                        intent.putExtra("image", Products.get(finalI + 1).getImageProduct());
                                        intent.putExtra("title", Products.get(finalI + 1).getProductTitle().trim());
                                        intent.putExtra("description", Products.get(finalI + 1).getProductDescription());
                                        intent.putExtra("price", ((int) Products.get(finalI + 1).getPrice()));
                                        intent.putExtra("colors", Products.get(finalI + 1).getColorList().trim());
                                        startActivity(intent);
                                    }
                                });
                                rowProducts.addView(child2);
                                i++;
                            }
                            tableProducts.addView(rowProducts, linie);
                            linie++;
                        }
                        appears = 1;
                        return true;

                    case R.id.events:
                        final List< Product> ProductsEvents =  databaseHandler.getProducts("evenimente");
                        scrollView = (ScrollView)findViewById(R.id.scrollView2);
                        tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
                        tableProducts.removeAllViews();

                        menuItem.setIcon(R.drawable.item2_filled);
                        listItems.get(0).setIcon(R.drawable.item1);
                        listItems.get(2).setIcon(R.drawable.item3);
                        listItems.get(3).setIcon(R.drawable.item4);
                        linie = 0;
                        for (int i = 0; i < ProductsEvents.size(); i++) {
                            View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                            ImageView imageView = (ImageView)view.findViewById(R.id.imageCard);
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(ProductsEvents.get(i).getImageProduct(), 0, ProductsEvents.get(i).getImageProduct().length));


                            TextView description = (TextView)view.findViewById(R.id.descriptionCard);
                            description.setText(ProductsEvents.get(i).getProductTitle().trim() + " \n");


                            TextView price = (TextView)view.findViewById(R.id.priceCard);
                            price.setText((int) ProductsEvents.get(i).getPrice() + " lei");


                            TableRow rowProducts = new TableRow(tableProducts.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                            rowProducts.setLayoutParams(lp);
                            final int finalI = i;
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                    intent.putExtra("id", ProductsEvents.get(finalI).getIdProduct());
                                    intent.putExtra("type", ProductsEvents.get(finalI).getType());
                                    intent.putExtra("image", ProductsEvents.get(finalI).getImageProduct());
                                    intent.putExtra("title", ProductsEvents.get(finalI).getProductTitle().trim());
                                    intent.putExtra("description", ProductsEvents.get(finalI).getProductDescription());
                                    intent.putExtra("price", ProductsEvents.get(finalI).getPrice());
                                    intent.putExtra("colors", ProductsEvents.get(finalI).getColorList().trim());
                                    startActivity(intent);
                                }
                            });
                            rowProducts.addView(view);
                            if (i + 1 < ProductsEvents.size()) {
                                View child2 = LayoutInflater.from(rowProducts.getContext()).inflate(R.layout.element1_col2, null);
                                ImageView imageView2 = (ImageView)child2.findViewById(R.id.imageView2);
                                imageView2.setImageBitmap(BitmapFactory.decodeByteArray(ProductsEvents.get(i + 1).getImageProduct(), 0, ProductsEvents.get(i + 1).getImageProduct().length));


                                TextView description2 = (TextView)child2.findViewById(R.id.textView2);
                                description2.setText(ProductsEvents.get(i + 1).getProductTitle().trim() + "\n");


                                TextView price2 = (TextView)child2.findViewById(R.id.textView3);
                                price2.setText(((int) ProductsEvents.get(i + 1).getPrice()) + " lei");
                                child2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                        intent.putExtra("id", ProductsEvents.get(finalI + 1).getIdProduct());
                                        intent.putExtra("type", ProductsEvents.get(finalI + 1).getType());
                                        intent.putExtra("image", ProductsEvents.get(finalI + 1).getImageProduct());
                                        intent.putExtra("title", ProductsEvents.get(finalI + 1).getProductTitle().trim());
                                        intent.putExtra("description", ProductsEvents.get(finalI + 1).getProductDescription());
                                        intent.putExtra("price", ProductsEvents.get(finalI + 1).getPrice());
                                        intent.putExtra("colors", ProductsEvents.get(finalI + 1).getColorList().trim());
                                        startActivity(intent);
                                    }
                                });
                                rowProducts.addView(child2);
                                i++;
                            }
                            tableProducts.addView(rowProducts, linie);
                            linie++;
                        }


                        return true;

                    case R.id.mart:
                        final List< Product> ProductsMartisor =  databaseHandler.getProducts("martisor");
                        scrollView = (ScrollView)findViewById(R.id.scrollView2);
                        tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
                        tableProducts.removeAllViews();

                        menuItem.setIcon(R.drawable.item3_filled);
                        listItems.get(0).setIcon(R.drawable.item1);
                        listItems.get(1).setIcon(R.drawable.item2);
                        listItems.get(3).setIcon(R.drawable.item4);
                        linie = 0;
                        for (int i = 0; i < ProductsMartisor.size(); i++) {
                            View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                            ImageView imageView = (ImageView)view.findViewById(R.id.imageCard);
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(ProductsMartisor.get(i).getImageProduct(), 0, ProductsMartisor.get(i).getImageProduct().length));


                            TextView description = (TextView)view.findViewById(R.id.descriptionCard);
                            description.setText(ProductsMartisor.get(i).getProductTitle().trim() + "\n");


                            TextView price = (TextView)view.findViewById(R.id.priceCard);
                            price.setText(ProductsMartisor.get(i).getPrice() + " lei");


                            TableRow rowProducts = new TableRow(tableProducts.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                            rowProducts.setLayoutParams(lp);

                            final int finalI = i;
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                    intent.putExtra("id", ProductsMartisor.get(finalI).getIdProduct());
                                    intent.putExtra("type", ProductsMartisor.get(finalI).getType());
                                    intent.putExtra("image", ProductsMartisor.get(finalI).getImageProduct());
                                    intent.putExtra("title", ProductsMartisor.get(finalI).getProductTitle().trim());
                                    intent.putExtra("description", ProductsMartisor.get(finalI).getProductDescription());
                                    intent.putExtra("price", ProductsMartisor.get(finalI).getPrice());
                                    intent.putExtra("colors", ProductsMartisor.get(finalI).getColorList().trim());
                                    startActivity(intent);
                                }
                            });
                            rowProducts.addView(view);
                            if (i + 1 < ProductsMartisor.size()) {
                                View child2 = LayoutInflater.from(rowProducts.getContext()).inflate(R.layout.element1_col2, null);
                                ImageView imageView2 = (ImageView)child2.findViewById(R.id.imageView2);
                                imageView2.setImageBitmap(BitmapFactory.decodeByteArray(ProductsMartisor.get(i + 1).getImageProduct(), 0, ProductsMartisor.get(i + 1).getImageProduct().length));


                                TextView description2 = (TextView)child2.findViewById(R.id.textView2);
                                description2.setText(ProductsMartisor.get(i + 1).getProductTitle().trim() + "\n");


                                TextView price2 = (TextView)child2.findViewById(R.id.textView3);
                                price2.setText(((int) ProductsMartisor.get(i + 1).getPrice()) + " lei");
                                child2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                        intent.putExtra("id", ProductsMartisor.get(finalI + 1).getIdProduct());
                                        intent.putExtra("type", ProductsMartisor.get(finalI + 1).getType());
                                        intent.putExtra("image", ProductsMartisor.get(finalI + 1).getImageProduct());
                                        intent.putExtra("title", ProductsMartisor.get(finalI + 1).getProductTitle().trim());
                                        intent.putExtra("description", ProductsMartisor.get(finalI + 1).getProductDescription());
                                        intent.putExtra("price", ProductsMartisor.get(finalI + 1).getPrice());
                                        intent.putExtra("colors", ProductsMartisor.get(finalI + 1).getColorList().trim());
                                        startActivity(intent);
                                    }
                                });
                                rowProducts.addView(child2);
                                i++;
                            }
                            tableProducts.addView(rowProducts, linie);
                            linie++;
                        }


                        return true;

                    case R.id.shop:
                        menuItem.setCheckable(false);
                        menuItem.setChecked(false);

                         if (appears < 2) {
                             mInterstitialAd.loadAd(new AdRequest.Builder().build());
                             mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {
                                    if (mInterstitialAd.isLoaded()) {
                                        appears++;
                                        mInterstitialAd.show();
                                    }
                                }
                             });
                        } else {
                            intent = new Intent(DecoMainActivity.this, ShopActivity.class);
                            startActivity(intent);
                        }
                        return true;

                    default:
                        return DecoMainActivity.super.onOptionsItemSelected(menuItem);
                }
            }
        });
        myMenu = bottomNavigationView.getMenu();
        selectedItem = myMenu.findItem(R.id.buchets);

        if (selectedItem.isChecked()) {
            final List< Product> ProductsMartisor =  databaseHandler.getProducts("buchet");
            scrollView = (ScrollView)findViewById(R.id.scrollView2);
            tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
            tableProducts.removeAllViews();

            selectedItem.setIcon(R.drawable.item1_filled);
            myMenu.findItem(R.id.events).setIcon(R.drawable.item2);
            myMenu.findItem(R.id.mart).setIcon(R.drawable.item3);
            myMenu.findItem(R.id.shop).setIcon(R.drawable.item4);
           int  linie = 0;
            for (int i = 0; i < ProductsMartisor.size(); i++) {
                View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                ImageView imageView = (ImageView)view.findViewById(R.id.imageCard);
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(ProductsMartisor.get(i).getImageProduct(), 0, ProductsMartisor.get(i).getImageProduct().length));


                TextView description = (TextView)view.findViewById(R.id.descriptionCard);
                description.setText(ProductsMartisor.get(i).getProductTitle().trim() + "\n");


                TextView price = (TextView)view.findViewById(R.id.priceCard);
                price.setText(ProductsMartisor.get(i).getPrice() + " lei");


                TableRow rowProducts = new TableRow(tableProducts.getContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                rowProducts.setLayoutParams(lp);

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                        intent.putExtra("id", ProductsMartisor.get(finalI).getIdProduct());
                        intent.putExtra("type", ProductsMartisor.get(finalI).getType());
                        intent.putExtra("image", ProductsMartisor.get(finalI).getImageProduct());
                        intent.putExtra("title", ProductsMartisor.get(finalI).getProductTitle().trim());
                        intent.putExtra("description", ProductsMartisor.get(finalI).getProductDescription());
                        intent.putExtra("price", ProductsMartisor.get(finalI).getPrice());
                        intent.putExtra("colors", ProductsMartisor.get(finalI).getColorList().trim());
                        startActivity(intent);
                    }
                });
                rowProducts.addView(view);
                if (i + 1 < ProductsMartisor.size()) {
                    View child2 = LayoutInflater.from(rowProducts.getContext()).inflate(R.layout.element1_col2, null);
                    ImageView imageView2 = (ImageView)child2.findViewById(R.id.imageView2);
                    imageView2.setImageBitmap(BitmapFactory.decodeByteArray(ProductsMartisor.get(i + 1).getImageProduct(), 0, ProductsMartisor.get(i + 1).getImageProduct().length));


                    TextView description2 = (TextView)child2.findViewById(R.id.textView2);
                    description2.setText(ProductsMartisor.get(i + 1).getProductTitle().trim() + "\n");


                    TextView price2 = (TextView)child2.findViewById(R.id.textView3);
                    price2.setText(((int) ProductsMartisor.get(i + 1).getPrice()) + " lei");
                    child2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                            intent.putExtra("id", ProductsMartisor.get(finalI + 1).getIdProduct());
                            intent.putExtra("type", ProductsMartisor.get(finalI + 1).getType());
                            intent.putExtra("image", ProductsMartisor.get(finalI + 1).getImageProduct());
                            intent.putExtra("title", ProductsMartisor.get(finalI + 1).getProductTitle().trim());
                            intent.putExtra("description", ProductsMartisor.get(finalI + 1).getProductDescription());
                            intent.putExtra("price", ProductsMartisor.get(finalI + 1).getPrice());
                            intent.putExtra("colors", ProductsMartisor.get(finalI + 1).getColorList().trim());
                            startActivity(intent);
                        }
                    });
                    rowProducts.addView(child2);
                    i++;
                }
                tableProducts.addView(rowProducts, linie);
                linie++;
            }
        }



    }

    public void findItems(View view) {
        String[] typeList = new String[]{"buchet", "evenimente", "martisor"};
        for (int i = 0; i <  myMenu.size() - 1; i++) {
            if (myMenu.getItem(i).isChecked()) {
                EditText editText = (EditText)findViewById(R.id.editText);
                if (editText.getText().toString().isEmpty()) {
                    final List<Product> productList = databaseHandler.getProducts(typeList[i]);
                    ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView2);
                    TableLayout tableProducts = (TableLayout) scrollView.findViewById(R.id.table_products);
                    tableProducts.removeAllViews();
                    int linie = 0;
                    for (int j = 0; j < productList.size(); j++) {
                        view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                        ImageView imageView1 = (ImageView) view.findViewById(R.id.imageCard);
                        imageView1.setImageBitmap(BitmapFactory.decodeByteArray(productList.get(j).getImageProduct(), 0, productList.get(j).getImageProduct().length));


                        TextView description1 = (TextView) view.findViewById(R.id.descriptionCard);
                        description1.setText(productList.get(j).getProductTitle().trim() + "\n");


                        TextView price1 = (TextView) view.findViewById(R.id.priceCard);
                        price1.setText(((int) productList.get(j).getPrice()) + " lei");


                        TableRow rowProducts = new TableRow(tableProducts.getContext());
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                        rowProducts.setLayoutParams(lp);
                        final int finalI = j;
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                intent.putExtra("id", productList.get(finalI).getIdProduct());
                                intent.putExtra("type", productList.get(finalI).getType());
                                intent.putExtra("image", productList.get(finalI).getImageProduct());
                                intent.putExtra("title", productList.get(finalI).getProductTitle().trim());
                                intent.putExtra("description", productList.get(finalI).getProductDescription());
                                intent.putExtra("price", productList.get(finalI).getPrice());
                                intent.putExtra("colors", productList.get(finalI).getColorList().trim());
                                startActivity(intent);
                            }
                        });
                        rowProducts.addView(view);
                        if (j + 1 < productList.size()) {
                            View child2 = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col2, null);
                            ;
                            ImageView imageView2 = (ImageView) child2.findViewById(R.id.imageView2);
                            imageView2.setImageBitmap(BitmapFactory.decodeByteArray(productList.get(j + 1).getImageProduct(), 0, productList.get(j + 1).getImageProduct().length));


                            TextView description2 = (TextView) child2.findViewById(R.id.textView2);
                            description2.setText(productList.get(j + 1).getProductTitle().trim() + "\n");


                            TextView price2 = (TextView) child2.findViewById(R.id.textView3);
                            price2.setText(((int) productList.get(j + 1).getPrice()) + " lei");
                            child2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                    intent.putExtra("id", productList.get(finalI + 1).getIdProduct());
                                    intent.putExtra("type", productList.get(finalI + 1).getType());
                                    intent.putExtra("image", productList.get(finalI + 1).getImageProduct());
                                    intent.putExtra("title", productList.get(finalI + 1).getProductTitle().trim());
                                    intent.putExtra("description", productList.get(finalI + 1).getProductDescription());
                                    intent.putExtra("price", productList.get(finalI + 1).getPrice());
                                    intent.putExtra("colors", productList.get(finalI + 1).getColorList().trim());
                                    startActivity(intent);
                                }
                            });
                            rowProducts.addView(child2);
                            j++;
                        }
                        tableProducts.addView(rowProducts, linie);
                        linie++;
                    }
                } else {
                    final List<Product> productList = databaseHandler.getProducts(typeList[i], editText.getText().toString().trim());

                    if (!productList.isEmpty()) {
                        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView2);
                        TableLayout tableProducts = (TableLayout) scrollView.findViewById(R.id.table_products);
                        tableProducts.removeAllViews();
                        int linie = 0;
                        for (int j = 0; j < productList.size(); j++) {
                            view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                            ImageView imageView1 = (ImageView) view.findViewById(R.id.imageCard);
                            imageView1.setImageBitmap(BitmapFactory.decodeByteArray(productList.get(j).getImageProduct(), 0, productList.get(j).getImageProduct().length));


                            TextView description1 = (TextView) view.findViewById(R.id.descriptionCard);
                            description1.setText(productList.get(j).getProductTitle().trim() + "\n");


                            TextView price1 = (TextView) view.findViewById(R.id.priceCard);
                            price1.setText(((int) productList.get(j).getPrice()) + " lei");


                            TableRow rowProducts = new TableRow(tableProducts.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                            rowProducts.setLayoutParams(lp);
                            final int finalI = j;
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                    intent.putExtra("id", productList.get(finalI).getIdProduct());
                                    intent.putExtra("type", productList.get(finalI).getType());
                                    intent.putExtra("image", productList.get(finalI).getImageProduct());
                                    intent.putExtra("title", productList.get(finalI).getProductTitle().trim());
                                    intent.putExtra("description", productList.get(finalI).getProductDescription());
                                    intent.putExtra("price", productList.get(finalI).getPrice());
                                    intent.putExtra("colors", productList.get(finalI).getColorList().trim());
                                    startActivity(intent);
                                }
                            });
                            rowProducts.addView(view);
                            if (j + 1 < productList.size()) {
                                View child2 = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col2, null);
                                ;
                                ImageView imageView2 = (ImageView) child2.findViewById(R.id.imageView2);
                                imageView2.setImageBitmap(BitmapFactory.decodeByteArray(productList.get(j + 1).getImageProduct(), 0, productList.get(j + 1).getImageProduct().length));


                                TextView description2 = (TextView) child2.findViewById(R.id.textView2);
                                description2.setText(productList.get(j + 1).getProductTitle().trim() + "\n");


                                TextView price2 = (TextView) child2.findViewById(R.id.textView3);
                                price2.setText(((int) productList.get(j + 1).getPrice()) + " lei");
                                child2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DecoMainActivity.this, ProductActivity.class);
                                        intent.putExtra("id", productList.get(finalI + 1).getIdProduct());
                                        intent.putExtra("type", productList.get(finalI + 1).getType());
                                        intent.putExtra("image", productList.get(finalI + 1).getImageProduct());
                                        intent.putExtra("title", productList.get(finalI + 1).getProductTitle().trim());
                                        intent.putExtra("description", productList.get(finalI + 1).getProductDescription());
                                        intent.putExtra("price", productList.get(finalI + 1).getPrice());
                                        intent.putExtra("colors", productList.get(finalI + 1).getColorList().trim());
                                        startActivity(intent);
                                    }
                                });
                                rowProducts.addView(child2);
                                j++;
                            }
                            tableProducts.addView(rowProducts, linie);
                            linie++;
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Nu s-a gasit niciun element !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

}
