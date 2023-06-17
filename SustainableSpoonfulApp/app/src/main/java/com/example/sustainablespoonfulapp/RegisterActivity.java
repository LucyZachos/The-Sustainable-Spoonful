package com.example.sustainablespoonfulapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;

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
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        //Check if any of the input fields are empty before inserting the customer details:
        if(name.isEmpty()||surname.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Please fill out all fields in this form.", Toast.LENGTH_SHORT).show();
            return; //Exit the method early
        }

        //If the password and confirm password match insert the details into the customer table:
        if(password.equals(confirmPassword)){
            //Getting a writable database:
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            //Checking if the email address already exists in the customer table:
            boolean emailExists = checkEmailExists(email,db);
            if(emailExists){
                Toast.makeText(RegisterActivity.this, "This email address already exists! Please try again.", Toast.LENGTH_SHORT).show();
            }else{ //Does not exist so insert details into the customer table:
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_CUSTOMER_NAME, name);
                values.put(DatabaseHelper.COLUMN_CUSTOMER_SURNAME, surname);
                values.put(DatabaseHelper.COLUMN_CUSTOMER_EMAIL,email);
                values.put(DatabaseHelper.COLUMN_CUSTOMER_PASSWORD, password);

                long rowID = db.insert(DatabaseHelper.TABLE_NAME_CUSTOMER,null,values);

                //Closing the database after inserting the customer's details:
                db.close();

                //If the row ID is not equal to minus one, display a success message:
                if(rowID != -1){
                    Toast.makeText(RegisterActivity.this, "Registration was successful!", Toast.LENGTH_SHORT).show();
                    //Redirect to the login page:
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button

                }else{
                    Toast.makeText(RegisterActivity.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }else{ //Passwords do not match, display an error message:
            Toast.makeText(RegisterActivity.this, "The passwords entered do not match! Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkEmailExists(String email, SQLiteDatabase db){
        //Define which column to retrieve from the database:
        String[] projection = {databaseHelper.getColumnEmail()};
        //Select any email addresses that are in the customer table that match the email address entered:
        String selection = databaseHelper.getColumnEmail() + " = ?";
        //Specify the argument for the query, this will be email:
        String[] selectionArgs = {email};
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