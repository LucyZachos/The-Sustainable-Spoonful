package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DiscountActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav_bar;

    CardView cardPicknpay;
    CardView cardCheckers;
    CardView cardWoolworths;
    CardView cardFoodLoversMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.search_bottom_navigation); //Set the search icon to selected when on this page:

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()){
                    //If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(DiscountActivity.this,LandingActivity.class)); //Redirect the customer to the home page:
                        return true;
                        //If the search icon is clicked,stay on the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(DiscountActivity.this,DiscountActivity.class)); //Stay on the search discount page:
                        return true;
                        //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(DiscountActivity.this,AccountActivity.class)); //Redirect the customer to the account page:
                        return true;
                    default:
                        return false;
                }
            }
        });

        //For the cards on the layout screen
        cardPicknpay = findViewById(R.id.cardPicknpay);
        cardCheckers = findViewById(R.id.cardCheckers);
        cardWoolworths = findViewById(R.id.cardWoolworths);
        cardFoodLoversMarket = findViewById(R.id.cardFoodLoversMarket);

        cardPicknpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiscountActivity.this,PicknPayProductsActivity.class);
                startActivity(intent);
            }
        });
        cardCheckers .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiscountActivity.this,CheckersProductsActivity.class);
                startActivity(intent);
            }
        });
        cardWoolworths .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiscountActivity.this,WoolworthsProductsActivity.class);
                startActivity(intent);
            }
        });
        cardFoodLoversMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiscountActivity.this,FoodLoversProductsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(DiscountActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false); //Set is logged in to true:

        //If the customer is not logged in, redirect to the home page:
        if(!isLoggedIn) {
            Toast.makeText(DiscountActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show(); //Display a message to the customer asking them to log in:
            startActivity(new Intent(DiscountActivity.this,MainActivity.class)); //Redirect to the main page of the application:
            finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
        }
    }
}
