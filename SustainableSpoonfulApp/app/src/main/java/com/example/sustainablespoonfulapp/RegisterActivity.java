package com.example.sustainablespoonfulapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    //Declaring the database helper variable:
    private DatabaseHelper databaseHelper;
    //Declaring variables for all the inputs in the registration form:
    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Creating an instance of the DatabaseHelper class:
        databaseHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.register_name_text);
        surnameEditText = findViewById(R.id.register_surname_text);
        emailEditText = findViewById(R.id.register_email_address_text);
        passwordEditText = findViewById(R.id.register_password_text);
        confirmPasswordEditText = findViewById(R.id.register_confirm_password_text);

        //Inserting the customers' details when pressing the confirm button in the registration form:
        Button confirmButton = findViewById(R.id.register_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                insertCustomer();
            }
        });

    }

    //Function to insert customer details into the customer table:
    private void insertCustomer(){
        //Getting all of the data that was input in the form:
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        //If the password and confirm password match insert the details into the customer table:
        if(password.equals(confirmPassword)){
            //Getting a writable database:
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, name);
            values.put(DatabaseHelper.COLUMN_SURNAME, surname);
            values.put(DatabaseHelper.COLUMN_EMAIL,email);
            values.put(DatabaseHelper.COLUMN_PASSWORD, password);

            long rowID = db.insert(DatabaseHelper.TABLE_NAME,null,values);

            //Closing the database after inserting the customer's details:
            db.close();

            //If a row is not equal to one, display a success message:
            if(rowID != -1){
                Toast.makeText(RegisterActivity.this, "Registration was successful!", Toast.LENGTH_SHORT).show();
                //Redirect to the login page:

            }else{
                Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
            }

        }else{ //Passwords do not match, display an error message:
            Toast.makeText(RegisterActivity.this, "The passwords entered do not match!", Toast.LENGTH_SHORT).show();
        }
    }
}