package com.example.decoroxx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;

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
                        TableRow rowProducts = (TableRow)tableProducts.findViewById(R.id.row_products);


                        return true;

                    case R.id.events:
                        menuItem.setIcon(R.drawable.item2_filled);
                        listItems.get(0).setIcon(R.drawable.item1);
                        listItems.get(2).setIcon(R.drawable.item3);
                        listItems.get(3).setIcon(R.drawable.item4);

                        return true;

                    case R.id.mart:
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

    }

    @Override
    public void onBackPressed() {

    }

}
