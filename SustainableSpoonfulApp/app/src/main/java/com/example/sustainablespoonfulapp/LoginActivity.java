package com.example.sustainablespoonfulapp;

import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    //Declaring the database helper variable:
    private DatabaseHelper databaseHelper;

    //Declaring variables for the email and password inputs:
    private  EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Creating an instant of the DatabaseHelper class:
        databaseHelper = new DatabaseHelper(this);
        emailEditText = findViewById(R.id.login_email_address_text);
        passwordEditText = findViewById(R.id.login_password_text);

        //Setting up the login button click listener:
        Button loginButton = findViewById(R.id.login_confirm_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
    }

    private void login(){
        //Getting the email and password that the customer has entered:
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //Check if any of the input fields are empty before logging in the customer:
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Please fill out all fields in this form.", Toast.LENGTH_SHORT).show();
            return; //Exit the method early
        }

        //Getting a readable database:
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        //Checking if the email and password match a record in the customer table:
        boolean loginSuccess = checkEmailAndPassword(email, password, db);

        //If a record was found and it matches/login was successful display a message and redirect to the landing page:
        if(loginSuccess){
            //Displaying a success message:
            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
            //Redirecting to the Landing Page:
            startActivity(new Intent(LoginActivity.this, LandingActivity.class));
            finish(); //Finishing the current activity so that users cannot go back to it when pressing the back button:
        }else{ //Password or email was incorrect:
            //Display an error message:
            Toast.makeText(LoginActivity.this, "Invalid email or password! Please try again.", Toast.LENGTH_SHORT).show();
        }

        //Closing the database:
        db.close();
    }

    private boolean checkEmailAndPassword(String email, String password, SQLiteDatabase db){
        //Define which column to retrieve from the database:
        String[] projection = {databaseHelper.getColumnEmail()};
        //Select any email addresses and passwords that are in the customer table that match the email address and password entered:
        String selection = databaseHelper.getColumnEmail() + " = ? AND " + databaseHelper.getColumnPassword() + " = ?";
        //Specify the arguments for the query, this will be email and password:
        String[] selectionArgs = {email, password};
        //Query the customer table for any matching records:
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_CUSTOMER, projection, selection, selectionArgs, null, null, null);
        //Checking if any records were found/records greater than 0:
        boolean exists = cursor.getCount()>0;
        //Close the cursor so that associated resources can be released:
        cursor.close();
        //Return the result (if the email exists or not):
        return exists;
    }
}