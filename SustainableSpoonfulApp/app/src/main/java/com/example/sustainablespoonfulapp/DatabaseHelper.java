package com.example.sustainablespoonfulapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Providing the database name and version:
    private static final String DATABASE_NAME = "sustainable_spoonful.db";
    private static final int DATABASE_VERSION = 1;

    //Providing the table name and column names:
    public static final String TABLE_NAME = "customer";
    private static final String COLUMN_ID = "customer_id";
    public static final String COLUMN_NAME = "customer_name";
    public static final String COLUMN_SURNAME = "customer_surname";
    public static final String COLUMN_EMAIL = "customer_email";
    public static final String COLUMN_PASSWORD = "customer_password";

    //Query to create the table:
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_SURNAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT)";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void  onCreate(SQLiteDatabase db){
        //Create the database table:
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Upgrade the database if necessary:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
