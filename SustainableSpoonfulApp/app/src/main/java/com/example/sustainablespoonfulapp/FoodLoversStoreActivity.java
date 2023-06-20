package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FoodLoversStoreActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_lovers_store_info); //Create the account page:


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
                        startActivity(new Intent(FoodLoversStoreActivity.this,DiscountActivity.class)); //Redirect the customer to the search discount page:
                        return true;
                    //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(FoodLoversStoreActivity.this,AccountActivity.class)); //Redirect the customer to the account page:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
