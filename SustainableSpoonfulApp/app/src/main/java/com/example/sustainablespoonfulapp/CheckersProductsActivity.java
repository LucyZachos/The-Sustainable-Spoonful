package com.example.sustainablespoonfulapp;

import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CheckersProductsActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav_bar;

    CardView cardSandwich;
    CardView cardOnions;
    CardView cardAce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_products);

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.search_bottom_navigation); //Set the search icon to selected when on this page:

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()){
                    //If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(CheckersProductsActivity.this,LandingActivity.class)); //Redirect the customer to the home page:
                        return true;
                    //If the search icon is clicked,stay on the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(CheckersProductsActivity.this,DiscountActivity.class)); //Stay on the search discount page:
                        return true;
                    //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(CheckersProductsActivity.this,AccountActivity.class)); //Redirect the customer to the account page:
                        return true;
                    default:
                        return false;
                }
            }
        });

        //For the cards on the layout screen
        cardSandwich = findViewById(R.id.cardSandwich);
        cardOnions = findViewById(R.id.cardOnions);
        cardAce = findViewById(R.id.cardAce);


        cardSandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Sandwich Clicked");
            }
        });
        cardOnions .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Onions Clicked");
            }
        });
        cardAce .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Pap Clicked");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(CheckersProductsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false); //Set is logged in to true:

        //If the customer is not logged in, redirect to the home page:
        if(!isLoggedIn) {
            Toast.makeText(CheckersProductsActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show(); //Display a message to the customer asking them to log in:
            startActivity(new Intent(CheckersProductsActivity.this,MainActivity.class)); //Redirect to the main page of the application:
            finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
        }
    }
}

