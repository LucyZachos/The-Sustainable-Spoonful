package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CheckersStoreActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav_bar;

    Button viewProductsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_store_info); //Create the account page:

        viewProductsButton = findViewById(R.id.view_products_button);

        //Redirects user to the Discounts page:
        viewProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckersStoreActivity.this, DiscountActivity.class);
                startActivity(intent);
            }
        });


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
                        startActivity(new Intent(CheckersStoreActivity.this,DiscountActivity.class)); //Redirect the customer to the search discount page:
                        return true;
                    //If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(CheckersStoreActivity.this,AccountActivity.class)); //Redirect the customer to the account page:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}
