package com.example.decoroxx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class DecoMainActivity extends AppCompatActivity {
    Menu myMenu;
    MenuItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deco_main);



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
                List<MenuItem> listItems = new ArrayList<>();
                listItems.add((MenuItem)myMenu.findItem(R.id.buchets));
                listItems.add((MenuItem)myMenu.findItem(R.id.events));
                listItems.add((MenuItem)myMenu.findItem(R.id.mart));
                listItems.add((MenuItem)myMenu.findItem(R.id.shop));

                Intent intent;
                switch(menuItem.getItemId()) {
                    case R.id.buchets:
                        menuItem.setIcon(R.drawable.item1_filled);
                        listItems.get(1).setIcon(R.drawable.item2);
                        listItems.get(2).setIcon(R.drawable.item3);
                        listItems.get(3).setIcon(R.drawable.item4);

                        menuItem.setChecked(true);
                        menuItem.setCheckable(true);
                        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView2);
                        TableLayout tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
                        for (int i = 0; i < 5; i++) {
                            View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                            LinearLayout cardView = (LinearLayout)view.findViewById(R.id.root);
                            ImageView imageView = (ImageView)cardView.findViewById(R.id.imageView);
                            imageView.setImageResource(R.drawable.as);


                            TextView description = (TextView)cardView.findViewById(R.id.description);
                            description.setText(R.string.buchet);


                            TextView price = (TextView)cardView.findViewById(R.id.price);
                            price.setText("30 lei");


                            TableRow rowProducts = new TableRow(tableProducts.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                            rowProducts.setLayoutParams(lp);
                            View child1 = (LayoutInflater.from(rowProducts.getContext())).inflate(R.layout.element1_col2, null);
                            rowProducts.addView(child1);
                            View child2 = LayoutInflater.from(rowProducts.getContext()).inflate(R.layout.element1_col2, null);
                            child2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                                    startActivity(intent);
                                }
                            });
                            rowProducts.addView(child2);
                            tableProducts.addView(rowProducts, i);
                        }

                        return true;

                    case R.id.events:
                        scrollView = (ScrollView)findViewById(R.id.scrollView2);
                        tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
                        tableProducts.removeAllViews();

                        menuItem.setIcon(R.drawable.item2_filled);
                        listItems.get(0).setIcon(R.drawable.item1);
                        listItems.get(2).setIcon(R.drawable.item3);
                        listItems.get(3).setIcon(R.drawable.item4);

                        return true;

                    case R.id.mart:
                        scrollView = (ScrollView)findViewById(R.id.scrollView2);
                        tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
                        tableProducts.removeAllViews();

                        menuItem.setIcon(R.drawable.item3_filled);
                        listItems.get(0).setIcon(R.drawable.item1);
                        listItems.get(1).setIcon(R.drawable.item2);
                        listItems.get(3).setIcon(R.drawable.item4);

                        return true;

                    case R.id.shop:
                        menuItem.setCheckable(false);
                        menuItem.setChecked(false);
                        intent = new Intent(DecoMainActivity.this, ShopActivity.class);
                        startActivity(intent);
                        return true;

                    default:
                        return DecoMainActivity.super.onOptionsItemSelected(menuItem);
                }
            }
        });
        myMenu = bottomNavigationView.getMenu();
        selectedItem = myMenu.findItem(R.id.buchets);
        if (selectedItem.isChecked()) {
            selectedItem.setIcon(R.drawable.item1_filled);
            (myMenu.findItem(R.id.events)).setIcon(R.drawable.item2);
            (myMenu.findItem(R.id.mart)).setIcon(R.drawable.item3);
            (myMenu.findItem(R.id.shop)).setIcon(R.drawable.item4);

            ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView2);
            TableLayout tableProducts = (TableLayout)scrollView.findViewById(R.id.table_products);
            for (int i = 0; i < 5; i++) {
                View view = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.element1_col1, null);
                LinearLayout cardView = (LinearLayout)view.findViewById(R.id.root);
                ImageView imageView = (ImageView)cardView.findViewById(R.id.imageView);
                imageView.setImageResource(R.drawable.as);


                TextView description = (TextView)cardView.findViewById(R.id.description);
                description.setText(R.string.buchet);


                TextView price = (TextView)cardView.findViewById(R.id.price);
                price.setText("30 lei");


                TableRow rowProducts = new TableRow(tableProducts.getContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                rowProducts.setLayoutParams(lp);
                View child1 = (LayoutInflater.from(rowProducts.getContext())).inflate(R.layout.element1_col2, null);
                rowProducts.addView(child1);
                View child2 = LayoutInflater.from(rowProducts.getContext()).inflate(R.layout.element1_col2, null);
                child2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                        startActivity(intent);
                    }
                });
                rowProducts.addView(child2);
                tableProducts.addView(rowProducts, i);
            }

        }

    }


}
