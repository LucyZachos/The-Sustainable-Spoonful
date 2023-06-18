package com.example.sustainablespoonfulapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;


public class AccountActivity extends AppCompatActivity{

    BottomNavigationView bottom_nav_bar;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account); //Create the account page:

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = sharedPreferences.getString("email","");

        //If the customer is not logged in, display a message and redirect to the main page when opening the application:
        if(email.isEmpty()){
            Toast.makeText(AccountActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show();  //Display a message to the customer asking them to log in:
            startActivity(new Intent(AccountActivity.this, MainActivity.class)); //Redirect the customer to the main page when opening the application:
            finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
            return; //Return early so that the rest of the method is not executed:
        }

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        //bottom_nav_bar.setSelectedItemId(R.id.account_bottom_navigation); //Set the account icon to selected when on this page:
        logoutButton = findViewById(R.id.account_logout_button);

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()){
                    //If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(AccountActivity.this,LandingActivity.class)); //Redirect the customer to the home page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        return true;
                        //If the search icon is clicked,go to the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(AccountActivity.this,DiscountActivity.class)); //Redirect the customer to the search discount page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        return true;
                        //If the account icon is clicked, stay on the account page:
                    case R.id.account_bottom_navigation:
                        return true;
                    default:
                        return false;
                }
            }
        });

        //When the logout button is clicked, call the showLogoutConfirmationBox() function:
        logoutButton.setOnClickListener(v -> {
            showLogoutConfirmationBox();
        });
    }

    //Function to display a confirmation box to the customer asking if they want to logout:
    private void showLogoutConfirmationBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout") //Title of confirmation box:
                .setMessage("Are you sure that you want to logout?") //Message in confirmation box:
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() { //Option 1: Customer clicks yes, call the logoutCustomer function:
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Log out the customer and redirect to the Home page:
                        logoutCustomer();
                    }
                })
                .setNegativeButton("No",null) //Option 2: Customer clicks no:
                .show();
    }

    private void logoutCustomer(){
        //Clear the customers' session:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email"); //Remove the email address that was stored in the session:
        editor.putBoolean("isLoggedIn",false);
        editor.apply(); //Save the changes to the SharedPreferences:

        //Display a message to the customer saying that they have logged out, then redirect to the main page when opening the application:
        Toast.makeText(AccountActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show(); //Display a message to the customer saying that they have logged out:

        //Redirect to the main page if the activity is active:
        Intent intent= new Intent(AccountActivity.this, MainActivity.class); //Redirect the customer to the main page when opening the application:
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
    }
}
