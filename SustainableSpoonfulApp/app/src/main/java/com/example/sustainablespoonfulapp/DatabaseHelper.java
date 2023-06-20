package com.example.sustainablespoonfulapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

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
    public static final String COLUMN_RETAILER_IMAGE = "retailer_image";

    //Discounted Products Table Constants:
    public static final String TABLE_NAME_DISCOUNTED_PRODUCTS = "discounted_products";
    public static final String COLUMN_DISCOUNT_ID = "discount_id";
    public static final String COLUMN_DISCOUNT_CODE = "discount_code";
    public static final String COLUMN_DISCOUNT_RETAILER_ID = "retailer_id";
    public static final String COLUMN_DISCOUNT_PRODUCT_NAME = "product_name";
    public static final String COLUMN_DISCOUNT_PERCENTAGE = "discount_percentage";
    public static final String COLUMN_DISCOUNT_IMAGE = "discount_image";

    private final Context context; // Add a member variable to store the Context object:

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; //Assign the context to the member variable:
    }

    @Override
    public void  onCreate(SQLiteDatabase db){ //Creates database tables and inserts initial data:
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
                COLUMN_RETAILER_ADDRESS + " TEXT," +
                COLUMN_RETAILER_IMAGE + " BLOB)"; //Retailer image column
        db.execSQL(createRetailerTable);

        //DISCOUNTED PRODUCTS TABLE:
        String createDiscountedProductsTable = "CREATE TABLE " + TABLE_NAME_DISCOUNTED_PRODUCTS + " (" +
                COLUMN_DISCOUNT_ID + " INTEGER PRIMARY KEY," +
                COLUMN_DISCOUNT_CODE + " TEXT," +
                COLUMN_DISCOUNT_PERCENTAGE + " TEXT," +
                COLUMN_DISCOUNT_PRODUCT_NAME + " TEXT," +
                COLUMN_DISCOUNT_RETAILER_ID + " INTEGER," +
                COLUMN_DISCOUNT_IMAGE + " BLOB," + //Discounted Product Image Column
                "FOREIGN KEY (" + COLUMN_DISCOUNT_RETAILER_ID + ") REFERENCES " +
                TABLE_NAME_RETAILER + "(" + COLUMN_RETAILER_ID + "))";
        db.execSQL(createDiscountedProductsTable);

        //Function to insert the data into the Retailer Table:
        insertRetailerData(db);

        //Function to insert the data into the Discounted Products Table:
        insertDiscountedProductsData(db);
    }

    //Function to convert drawable resources to bitmap:
    private byte[] convertImageToByteArray(int imageResource){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    //Method to retrieve all the store images from the retailer table:
    public List<byte[]> getAllStoreImages() {
        List<byte[]> storeImages = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT " + COLUMN_RETAILER_IMAGE + " FROM " + TABLE_NAME_RETAILER; //query to select all retailer images from the retailer table:
        Cursor cursor = database.rawQuery(query, null);
        int columnIndex = cursor.getColumnIndex(COLUMN_RETAILER_IMAGE);

        if(columnIndex != -1){
            if(cursor.moveToFirst()){
                do{
                    byte[] image = cursor.getBlob(columnIndex);
                    storeImages.add(image);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return storeImages;
    }

    private void insertRetailerData(SQLiteDatabase db){
        //Create a ContentValues object so that it can hold the column values for each row:
        ContentValues values = new ContentValues();

        //Inserting the first shop:
        values.put(COLUMN_RETAILER_NAME, "PicknPay");
        values.put(COLUMN_RETAILER_ADDRESS, "Greenstone");
        values.put(COLUMN_RETAILER_IMAGE, convertImageToByteArray(R.drawable.picknpay_discount));
        db.insert(TABLE_NAME_RETAILER, null, values);

        //Inserting the second shop:
        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_RETAILER_NAME, "Woolworths");
        values.put(COLUMN_RETAILER_ADDRESS, "Greenstone");
        values.put(COLUMN_RETAILER_IMAGE, convertImageToByteArray(R.drawable.woolworths_discount));
        db.insert(TABLE_NAME_RETAILER, null, values);

        //Inserting the third shop:
        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_RETAILER_NAME, "Checkers");
        values.put(COLUMN_RETAILER_ADDRESS, "Meadowdale");
        values.put(COLUMN_RETAILER_IMAGE, convertImageToByteArray(R.drawable.checkers_discount));
        db.insert(TABLE_NAME_RETAILER, null, values);

        //Inserting the fourth shop:
        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_RETAILER_NAME, "Food Lover's Market");
        values.put(COLUMN_RETAILER_ADDRESS, "Greenstone");
        values.put(COLUMN_RETAILER_IMAGE, convertImageToByteArray(R.drawable.foodloversmarket_discount));
        db.insert(TABLE_NAME_RETAILER, null, values);
    }

    public void insertDiscountedProductsData(SQLiteDatabase db){
        //Create a ContentValues object so that it can hold the column values for each row:
        ContentValues values = new ContentValues();

        //Insert the first discounted product:
        values.put(COLUMN_DISCOUNT_CODE, "P1CkNP@yI1");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "10%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "White Bread Loaf");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 1); //PicknPay is the first store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.bread));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "P1CkNP@yO2");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "20%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Oranges 1 KG");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 1); //PicknPay is the first store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.oranges));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "P1CkNP@yS0");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "30%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Sunflower Oil 1 L");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 1); //PicknPay is the first store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.oil));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        //Insert the first discounted product:
        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "W0oLWORTHSM3");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "15%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Full Cream Milk 1 L");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 2); //Woolworths is the second store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.milk));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "W0oLWORTHSBB");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "10%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Pack of Bananas");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 2); //Woolworths is the second store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.bananas));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "W0oLWORTHSA@");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "30%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Apples 1.5 KG");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 2); //Woolworths is the second store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.apples));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "CHECKER$U$");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "60%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Ultimate Sandwich");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 3); //Checkers is the third store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.sandwich));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "CHECKER$OO");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "35%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Onions 1 KG");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 3); //Checkers is the third store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.onions));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "CHECKER$AP");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "60%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Ace Pap 1 KG");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 3); //Checkers is the third store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.ace_pap));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "F00DLOVER$R1");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "40%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Brown Rice 1 KG");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 4); //Food Lover's Market is the fourth store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.brownrice));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "F00DLOVER$UT");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "60%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "UTD Potatoes 1 KG");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 4); //Food Lover's Market is the fourth store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.potatoes));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "F00DLOVER$SC");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "70%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Strawberry Chocolate Cake");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 4); //Food Lover's Market is the fourth store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.cake));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);

        values.clear(); //Clear the ContentValues object so that it can be reused:
        values.put(COLUMN_DISCOUNT_CODE, "F00DLOVER$BB");
        values.put(COLUMN_DISCOUNT_PERCENTAGE, "80%");
        values.put(COLUMN_DISCOUNT_PRODUCT_NAME, "Bread Rolls x6");
        values.put(COLUMN_DISCOUNT_RETAILER_ID, 4); //Food Lover's Market is the fourth store that is loaded:
        values.put(COLUMN_DISCOUNT_IMAGE, convertImageToByteArray(R.drawable.rolls));
        db.insert(TABLE_NAME_DISCOUNTED_PRODUCTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){  //Upgrade the database schema if necessary:
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
