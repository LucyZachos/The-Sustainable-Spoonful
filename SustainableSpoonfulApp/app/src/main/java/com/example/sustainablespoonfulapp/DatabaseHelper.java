package com.example.sustainablespoonfulapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Providing the database name and version:
    public static final String DATABASE_NAME = "sustainable_spoonful.db";
    public static final int DATABASE_VERSION = 1;

    //Customer Table Constants:
    public static final String TABLE_NAME_CUSTOMER = "customer";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_CUSTOMER_NAME = "customer_name";
    public static final String COLUMN_CUSTOMER_SURNAME = "customer_surname";
    public static final String COLUMN_CUSTOMER_EMAIL = "customer_email";
    public static final String COLUMN_CUSTOMER_PASSWORD = "customer_password";

    //Retailer Table Constants:
    public static final String TABLE_NAME_RETAILER = "retailer";
    public static final String COLUMN_RETAILER_ID = "retailer_id";
    public static final String COLUMN_RETAILER_NAME = "retailer_name";
    public static final String COLUMN_RETAILER_ADDRESS = "address";

    //Discounted Products Table Constants:
    public static final String TABLE_NAME_DISCOUNTED_PRODUCTS = "discounted_products";
    public static final String COLUMN_DISCOUNT_ID = "discount_id";
    public static final String COLUMN_DISCOUNT_CODE = "discount_code";
    public static final String COLUMN_DISCOUNT_RETAILER_ID = "retailer_id";
    public static final String COLUMN_DISCOUNT_PRODUCT_NAME = "product_name";
    public static final String COLUMN_DISCOUNT_PERCENTAGE = "discount_percentage";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void  onCreate(SQLiteDatabase db){
        //Create the database tables:
        //CUSTOMER TABLE:
        String createCustomerTable = "CREATE TABLE " + TABLE_NAME_CUSTOMER + " (" +
                COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY," +
                COLUMN_CUSTOMER_NAME + " TEXT," +
                COLUMN_CUSTOMER_SURNAME + " TEXT," +
                COLUMN_CUSTOMER_EMAIL + " TEXT," +
                COLUMN_CUSTOMER_PASSWORD + " TEXT)";
        db.execSQL(createCustomerTable);

        //RETAILER TABLE:
        String createRetailerTable = "CREATE TABLE " + TABLE_NAME_RETAILER + " (" +
                COLUMN_RETAILER_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RETAILER_NAME + " TEXT," +
                COLUMN_RETAILER_ADDRESS + " TEXT)";
        db.execSQL(createRetailerTable);

        //DISCOUNTED PRODUCTS TABLE:
        String createDiscountedProductsTable = "CREATE TABLE " + TABLE_NAME_DISCOUNTED_PRODUCTS + " (" +
                COLUMN_DISCOUNT_ID + " INTEGER PRIMARY KEY," +
                COLUMN_DISCOUNT_CODE + " TEXT," +
                COLUMN_DISCOUNT_PERCENTAGE + " TEXT," +
                COLUMN_DISCOUNT_PRODUCT_NAME + " TEXT," +
                COLUMN_DISCOUNT_RETAILER_ID + " INTEGER," +
                "FOREIGN KEY (" + COLUMN_DISCOUNT_RETAILER_ID + ") REFERENCES " +
                TABLE_NAME_RETAILER + "(" + COLUMN_RETAILER_ID + "))";
        db.execSQL(createDiscountedProductsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Upgrade the database if necessary:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RETAILER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DISCOUNTED_PRODUCTS);
        onCreate(db);
    }

    //Use a get method for the email column:
    public String getColumnEmail(){
        return COLUMN_CUSTOMER_EMAIL;
    }

    //Use a get method for the password column:
    public String getColumnPassword(){
        return COLUMN_CUSTOMER_PASSWORD;
    }

}
