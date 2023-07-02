package com.example.sustainablespoonfulapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.widget.Button;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EditActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav_bar;
    Button confirmButton;
    EditText nameEditText;
    EditText surnameEditText;
    EditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account); //Create the edit account page:

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = sharedPreferences.getString("email", "");

        //If the customer is not logged in, display a message and redirect to the main page when opening the application:
        if (email.isEmpty()) {
            Toast.makeText(EditActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show();  //Display a message to the customer asking them to log in:
            startActivity(new Intent(EditActivity.this, MainActivity.class)); //Redirect the customer to the main page when opening the application:
            finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
            return; //Return early so that the rest of the method is not executed:
        }

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.account_bottom_navigation); //Set the account icon to selected when on this page:
        nameEditText = findViewById(R.id.edit_account_name_text);
        surnameEditText = findViewById(R.id.edit_account_surname_text);
        emailEditText = findViewById(R.id.edit_account_email_address_text);
        confirmButton = findViewById(R.id.edit_account_confirm_button);

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()) {
                    //If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(EditActivity.this, LandingActivity.class)); //Redirect the customer to the home page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        return true;
                    //If the search icon is clicked,go to the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(EditActivity.this, DiscountActivity.class)); //Redirect the customer to the search discount page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        return true;
                    //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        onBackPressed();
                        return true;
                    default:
                        return false;
                }
            }
        });

        //Load the customer details from the database:
        loadCustomerDetails();

        //When the confirm button is clicked, call the updateCustomerDetails() function:
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateCustomerDetails();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(EditActivity.this, AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    //Function to load customer details from the database:
    private void loadCustomerDetails() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = sharedPreferences.getString("email", "");

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_CUSTOMER_NAME,
                DatabaseHelper.COLUMN_CUSTOMER_SURNAME,
                DatabaseHelper.COLUMN_CUSTOMER_EMAIL
        };

        String selection = DatabaseHelper.COLUMN_CUSTOMER_EMAIL + " =?";
        String[] selectionArgs = {email};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME_CUSTOMER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            //Get the column indexes of the customers' name, surname and email:
            int customerNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER_NAME);
            int customerSurnameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER_SURNAME);
            int customerEmailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER_EMAIL);

            //Get the strings from the index positions:
            String customerName = cursor.getString(customerNameIndex);
            String customerSurname = cursor.getString(customerSurnameIndex);
            String customerEmail = cursor.getString(customerEmailIndex);

            cursor.close(); //Closing the cursor:
            database.close(); //Closing the database connection:

            //Setting the values from the database into the 3 text boxes on the page:
            nameEditText.setText(customerName);
            surnameEditText.setText(customerSurname);
            emailEditText.setText(customerEmail);
        }
    }

    //Function to update customer details:
    private void updateCustomerDetails() {
        //Retrieve the name, surname and email address from the form, convert it to string and trim it:
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String newEmail = emailEditText.getText().toString().trim();

        //If any fields are left empty display an error message:
        if (name.isEmpty() || surname.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(EditActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the new email exists in the database:
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String[] projection = {DatabaseHelper.COLUMN_CUSTOMER_EMAIL};
        String selection = DatabaseHelper.COLUMN_CUSTOMER_EMAIL + "=?";
        String[] selectionArgs = {newEmail};

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME_CUSTOMER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Email already exists in the database but check if it is the same as the current email in the SharedPreferences:
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String currentEmail = sharedPreferences.getString("email", "");

            //If the email in SharedPreferences matches the new email, only update the name and surname of the customer:
            if (currentEmail.equals(newEmail)) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_CUSTOMER_NAME, name);
                values.put(DatabaseHelper.COLUMN_CUSTOMER_SURNAME, surname);

                String whereClause = DatabaseHelper.COLUMN_CUSTOMER_EMAIL + "=?";
                String[] whereArgs = {currentEmail};

                int rowsAffected = database.update(DatabaseHelper.TABLE_NAME_CUSTOMER, values, whereClause, whereArgs);
                database.close(); //Close the database connection:

                if (rowsAffected > 0) {
                    //Was successful, display a success message to the customer:
                    Toast.makeText(EditActivity.this, "Your account details have been updated successfully!", Toast.LENGTH_SHORT).show();

                    // Update the stored name and surname in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("surname", surname);
                    editor.apply();

                    //Redirect the customer to the Account Page:
                    Intent intent = new Intent(EditActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //Failed, display an error message to the customer:
                    Toast.makeText(EditActivity.this, "Failed to update account details. Please try again!", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Email already exists and its' different from the current email, display an error message:
                cursor.close(); //Close the cursor:
                database.close(); //Close the database connection:
                Toast.makeText(EditActivity.this, "Email already exists. Please try again!", Toast.LENGTH_SHORT).show();
            }
        } else { //Email does not exist, so update the name, surname and email in the database:
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_CUSTOMER_NAME, name);
            values.put(DatabaseHelper.COLUMN_CUSTOMER_SURNAME, surname);
            values.put(DatabaseHelper.COLUMN_CUSTOMER_EMAIL, newEmail);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String currentEmail = sharedPreferences.getString("email", "");

            String whereClause = DatabaseHelper.COLUMN_CUSTOMER_EMAIL + "=?";
            String[] whereArgs = {currentEmail};

            int rowsAffected = database.update(DatabaseHelper.TABLE_NAME_CUSTOMER, values, whereClause, whereArgs);
            database.close(); //Close the database connection:

            if (rowsAffected > 0) {
                //Was successful, display a success message to the customer:
                Toast.makeText(EditActivity.this, "Your account details have been updated successfully!", Toast.LENGTH_SHORT).show();
                // Update the stored email in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", newEmail);
                editor.apply();

                //Redirect the customer to the Account Page:
                Intent intent = new Intent(EditActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            } else {
                //Failed, display an error message to the customer:
                Toast.makeText(EditActivity.this, "Failed to update customer details", Toast.LENGTH_SHORT).show();
            }
        }
    }
}