package com.example.decoroxx;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private SQLiteDatabase myDataBase;
    private final Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "decoroxx";
    private static String DATABASE_PATH = "";
    private final File DB_FILE;
    private static final String TABLE_PRODUCT = "Product";
    private static final String TABLE_SHOP = "Shop";
    private static final String ID_KEY = "ID";
    private static final String TYPE_KEY = "TYPE";
    private static final String IMG_KEY = "IMAGE";
    private static final String TITLE_KEY = "TITLE";
    private static final String DESCRIPTION_KEY = "DESCRIPTION";
    private static final String PRICE_KEY = "PRICE";
    private static final String COLORS_KEY = "COLORS";
    private static final String QUAN_KEY = "QUANTITY";
    private static final String IDPROD_KEY = "IDPROD";
    private static final String SELCOL_KEY = "SELCOL";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" +
                                                ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_KEY + " VARCHAR(15) NOT NULL," + IMG_KEY + " INTEGER NOT NULL,"
                                                + TITLE_KEY + " VARCHAR(255) UNIQUE NOT NULL," + DESCRIPTION_KEY + " VARCHAR(255) NOT NULL,"
                                                + PRICE_KEY + " DOUBLE NOT NULL," + COLORS_KEY + " VARCHAR(255) NOT NULL" + ")";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    private static String SELECT_ALL = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + TYPE_KEY + "=?";
    private static String SELECT_ALL_BY_TITLE = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + TYPE_KEY + "=? AND " + TITLE_KEY + "=?";
    private static String SELECT_SHOP = "SELECT * FROM " + TABLE_SHOP;
    private static String FIND_SHOP_ELEMENT = "SELECT * FROM " + TABLE_SHOP + " WHERE " + IDPROD_KEY;
    private static String INSERT_SHOP = "INSERT or replace INTO " + TABLE_SHOP + "(" + TYPE_KEY + "," + IMG_KEY + "," + TITLE_KEY + "," + PRICE_KEY + "," + COLORS_KEY + "," + IDPROD_KEY + "," + DESCRIPTION_KEY + "," + SELCOL_KEY + ")" + " VALUES";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DB_FILE = context.getDatabasePath(DATABASE_NAME);
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

    public boolean checkDataBase() {
        return DB_FILE.exists();
    }

    public void copyDataBase() throws IOException {
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(DB_FILE);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void createDataBase() {
        if (checkDataBase() == false) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean openDatabase() {
        myDataBase = SQLiteDatabase.openDatabase(String.valueOf(DB_FILE), null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase != null ? true: false;
    }

    public void closeDatabase(){
        if (!myDataBase.isOpen())
            return;
        myDataBase.close();
    }

    public Product findShopElement(Product prod) {
        openDatabase();
        myDataBase.enableWriteAheadLogging();
        Product productReturned = null;
        Cursor cursor = myDataBase.rawQuery(FIND_SHOP_ELEMENT + "=" + prod.getIdProduct(), null);
        if (cursor.moveToNext()) {
                    Product product = new Product();
                    product.setIdProduct(cursor.getInt(7));
                    product.setType(cursor.getString(1).trim());
                    product.setImageProduct(cursor.getBlob(2));
                    product.setProductTitle(cursor.getString(3).trim());
                    product.setProductDescription(cursor.getString(8).trim());
                    product.setPrice((int) cursor.getDouble(4));
                    product.setSelectedColor(cursor.getString(9));
                    product.setColorList(cursor.getString(5).trim());
                    product.setQuantity(cursor.getInt(6));
                    productReturned = product;
                    System.out.println(product);
        }
        closeDatabase();
        return productReturned;
    }

    public void addProduct(Product product) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(IDPROD_KEY, product.getIdProduct()); // product id
        values.put(TYPE_KEY, product.getType()); // product type
        values.put(IMG_KEY, product.getImageProduct()); // product image
        values.put(TITLE_KEY, product.getProductTitle()); // product title
        values.put(DESCRIPTION_KEY, product.getProductDescription()); // product description
        values.put(PRICE_KEY, product.getPrice()); // product type
        values.put(COLORS_KEY, product.getColorList()); // product colors
        values.put(QUAN_KEY, product.getQuantity()); //product quantity
        values.put(SELCOL_KEY, product.getSelectedColor()); //add selected colors of the product
        String copyInsert = INSERT_SHOP;
        INSERT_SHOP += "(" + "\"" + product.getType() + "\"" + "," + "\"" + product.getImageProduct() + "\"" + "," + "\"" +product.getProductTitle() + "\"" + "," + product.getPrice() + "," + "\"" + product.getColorList() + "\"" + "," +  product.getIdProduct()  + "," + "\"" + product.getProductDescription() + "\"" + "," + "\"" + product.getSelectedColor() + "\"" + ");";        // Inserting Row
        long result = myDataBase.insert(TABLE_SHOP, null, values);
        System.out.println(result);
       // myDataBase.execSQL(INSERT_SHOP);
        INSERT_SHOP = copyInsert;
        //2nd argument is String containing nullColumnHack
        closeDatabase();
    }

    public List<Product> getProductShop() {
        openDatabase();
        List<Product> productList = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery(SELECT_SHOP, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdProduct(cursor.getInt(7));
                product.setType(cursor.getString(1).trim());
                product.setImageProduct(cursor.getBlob(2));
                product.setProductTitle(cursor.getString(3).trim());
                product.setProductDescription(cursor.getString(8).trim());
                product.setPrice((int) cursor.getDouble(4));
                product.setSelectedColor(cursor.getString(9));
                product.setColorList(cursor.getString(5).trim());
                product.setQuantity(cursor.getInt(6));
                productList.add(product);
            }while (cursor.moveToNext());
        }
        closeDatabase();
        return productList;
    }

    public void updateQuantity(Product product, int quantity) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUAN_KEY, quantity);
        myDataBase.update(TABLE_SHOP, contentValues, IDPROD_KEY + "=" + product.getIdProduct(), null);
        closeDatabase();
    }

    public void updateColor(Product product, String color) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SELCOL_KEY, color);
        myDataBase.update(TABLE_SHOP, contentValues, IDPROD_KEY + "=" + product.getIdProduct(), null);
        closeDatabase();
    }

    public void deleteRow(Product product) {
        openDatabase();
        myDataBase.delete(TABLE_SHOP, IDPROD_KEY + "=" + product.getIdProduct(), null);
        closeDatabase();
    }

    public List<Product> getProducts(String type, String title) {
        openDatabase();
        List<Product> productsList = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery(SELECT_ALL, new String[]{type});
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdProduct(Integer.parseInt(cursor.getString(0)));
                product.setType(cursor.getString(1));
                product.setImageProduct(cursor.getBlob(2));
                product.setProductTitle(cursor.getString(3));
                product.setProductDescription(cursor.getString(4));
                product.setPrice(Integer.parseInt(cursor.getString(5)));
                product.setColorList(cursor.getString(6));
                // Adding product to list
                if (product.getProductTitle().trim().contains(title.trim()))
                    productsList.add(product);
            }while(cursor.moveToNext());
        }
        closeDatabase();
        return productsList;
    }

    public List<Product> getProducts(String type) {
        //myDataBase = this.getReadableDatabase();
        openDatabase();
        List<Product> productsList = new ArrayList<>();
      /*  Cursor cursor = db.query(TABLE_PRODUCT, new String[] { ID_KEY,
                        TYPE_KEY, IMG_KEY, TITLE_KEY, DESCRIPTION_KEY, PRICE_KEY, COLORS_KEY }, TYPE_KEY + "=?",
                new String[] { type }, null, null, null, null);*/
          //openDatabase();
        /*      SQLiteDatabase db = this.getWritableDatabase();
  */      Cursor cursor = myDataBase.rawQuery(SELECT_ALL, new String[]{type});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdProduct(Integer.parseInt(cursor.getString(0)));
                product.setType(cursor.getString(1));
                product.setImageProduct(cursor.getBlob(2));
                product.setProductTitle(cursor.getString(3));
                product.setProductDescription(cursor.getString(4));
                product.setPrice(Integer.parseInt(cursor.getString(5)));
                product.setColorList(cursor.getString(6));
                // Adding product to list  
                productsList.add(product);
            } while (cursor.moveToNext());
        }
        closeDatabase();
        // return products
        return productsList;
    }
}
