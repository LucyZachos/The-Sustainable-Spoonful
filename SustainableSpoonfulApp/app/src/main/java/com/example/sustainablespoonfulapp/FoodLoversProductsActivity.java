package com.example.sustainablespoonfulapp;

import android.view.View;
import android.widget.Toast;
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

public class FoodLoversProductsActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav_bar;

    CardView cardRice;
    CardView cardPotatoes;
    CardView cardCake;
    CardView cardRolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlovers_products);

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = sharedPreferences.getString("email","");

        //If the customer is not logged in, display a message and redirect to the main page when opening the application:
        if(email.isEmpty()){
            Toast.makeText(FoodLoversProductsActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show(); //Display a message to the customer asking them to log in:
            startActivity(new Intent(FoodLoversProductsActivity.this, MainActivity.class)); //Redirect the customer to the main page when opening the application:
            finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
            return; //Return early so that the rest of the method is not executed:
        }

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.search_bottom_navigation); //Set the search icon to selected when on this page:

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()){
                    //If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(FoodLoversProductsActivity.this,LandingActivity.class)); //Redirect the customer to the home page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        break;
                    //If the search icon is clicked,stay on the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(FoodLoversProductsActivity.this,DiscountActivity.class)); //Stay on the search discount page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        break;
                    //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(FoodLoversProductsActivity.this,AccountActivity.class)); //Redirect the customer to the account page:
                        finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
                        break;
                    default:
                }
                return true;
            }
        });

        //For the cards on the layout screen
        cardRice = findViewById(R.id.cardRice);
        cardPotatoes = findViewById(R.id.cardPotatoes);
        cardCake = findViewById(R.id.cardCake);
        cardRolls = findViewById(R.id.cardRolls);

        cardRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Rice Clicked");
            }
        });
        cardPotatoes .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Potatoes Clicked");
            }
        });
        cardCake .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Cake Clicked");
            }
        });
        cardRolls .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Rolls Clicked");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(FoodLoversProductsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}