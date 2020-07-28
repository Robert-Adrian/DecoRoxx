package com.example.decoroxx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DecoRoxx";
    private static final String TABLE_PRODUCT = "Product";
    private static final String ID_KEY = "ID";
    private static final String TYPE_KEY = "TYPE";
    private static final String IMG_KEY = "IMAGE";
    private static final String TITLE_KEY = "TITLE";
    private static final String DESCRIPTION_KEY = "DESCRIPTION";
    private static final String PRICE_KEY = "PRICE";
    private static final String COLORS_KEY = "COLORS";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" +
                                                ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_KEY + " VARCHAR(15) NOT NULL," + IMG_KEY + " INTEGER NOT NULL,"
                                                + TITLE_KEY + " VARCHAR(255) UNIQUE NOT NULL," + DESCRIPTION_KEY + " VARCHAR(255) NOT NULL,"
                                                + PRICE_KEY + " DOUBLE NOT NULL," + COLORS_KEY + " VARCHAR(255) NOT NULL" + ")";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    private String SELECT_ALL = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + TYPE_KEY + "=";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(DROP_TABLE);

        // Create tables again
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_KEY, product.getIdProduct()); // product id
        values.put(TYPE_KEY, product.getType()); // product type
        values.put(IMG_KEY, product.getImageProduct()); // product image
        values.put(TITLE_KEY, product.getProductTitle()); // product title
        values.put(DESCRIPTION_KEY, product.getProductDescription()); // product description
        values.put(PRICE_KEY, product.getPrice()); // product type
        String colorList = "";

        for (String color : product.getColorList()) {
            colorList += color + ";";
        }
        values.put(COLORS_KEY, colorList.substring(0, colorList.length() - 1)); // product colors

        // Inserting Row  
        db.insert(TABLE_PRODUCT, null, values);
        //2nd argument is String containing nullColumnHack  
        db.close(); // Closing database connection  
    }
    
    public List<Product> getProducts(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> productsList = new ArrayList<>();
      /*  Cursor cursor = db.query(TABLE_PRODUCT, new String[] { ID_KEY,
                        TYPE_KEY, IMG_KEY, TITLE_KEY, DESCRIPTION_KEY, PRICE_KEY, COLORS_KEY }, TYPE_KEY + "=?",
                new String[] { type }, null, null, null, null);*/

          SELECT_ALL += type;
  /*      SQLiteDatabase db = this.getWritableDatabase();
  */      Cursor cursor = db.rawQuery(SELECT_ALL, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdProduct(Integer.parseInt(cursor.getString(0)));
                product.setType(cursor.getString(1));
                product.setImageProduct(Integer.parseInt(cursor.getString(2)));
                product.setProductTitle(cursor.getString(3));
                product.setProductDescription(cursor.getString(4));
                product.setPrice(Double.parseDouble(cursor.getString(5)));
                String[] colorList = cursor.getString(6).split(";");
                product.setColorList(Arrays.asList(colorList));
                // Adding product to list  
                productsList.add(product);
            } while (cursor.moveToNext());
        }
        
        // return products
        return productsList;
    }
}
