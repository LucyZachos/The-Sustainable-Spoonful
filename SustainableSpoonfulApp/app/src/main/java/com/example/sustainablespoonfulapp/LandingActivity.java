package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.view.View;

public class LandingActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav_bar;

    ImageButton landing_picknpay_button, landing_foodloversmarket_button, landing_checkers_button, landing_woolworths_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing); //Create the home page:

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.home_bottom_navigation); //Set the home icon to selected when on this page:

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()){
                    //If the home icon is clicked, stay on the home page:
                    case R.id.home_bottom_navigation:
                        return true;
                    //If the search icon is clicked, go to the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(LandingActivity.this,DiscountActivity.class)); //Redirect the customer to the search discount page:
                        return true;
                    //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(LandingActivity.this,AccountActivity.class)); //Redirect the customer to the account page:
                        return true;
                    default:
                        return false;
                }
            }
        });

        //For the image buttons on the landing screen:
        landing_picknpay_button = findViewById(R.id.landing_picknpay_button);
        landing_foodloversmarket_button = findViewById(R.id.landing_foodloversmarket_button);
        landing_checkers_button = findViewById(R.id.landing_checkers_button);
        landing_woolworths_button = findViewById(R.id.landing_woolworths_button);

        landing_picknpay_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LandingActivity.this,PicknPayStoreActivity.class);
                startActivity(intent);
            }
        });

        landing_foodloversmarket_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LandingActivity.this,FoodLoversStoreActivity.class);
                startActivity(intent);
            }
        });

        landing_checkers_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LandingActivity.this,CheckersStoreActivity.class);
                startActivity(intent);
            }
        });

        landing_woolworths_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LandingActivity.this,WoolworthsStoreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false); //Set is logged in to true:

        //If the customer is not logged in, redirect to the home page:
        if(!isLoggedIn) {
            Toast.makeText(LandingActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show(); //Display a message to the customer asking them to log in:
            startActivity(new Intent(LandingActivity.this,MainActivity.class)); //Redirect to the main page of the application:
            finish(); //Finishing the current activity so that customers' cannot go back to it when pressing the back button:
        }
    }
}
