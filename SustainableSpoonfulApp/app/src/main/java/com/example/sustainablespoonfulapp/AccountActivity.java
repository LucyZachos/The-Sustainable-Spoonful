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


public class AccountActivity extends AppCompatActivity{

    BottomNavigationView bottom_nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = sharedPreferences.getString("email","");

        //If the customer is not logged in, display a message and redirect to the home page:
        if(email.isEmpty()){
            Toast.makeText(AccountActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AccountActivity.this, MainActivity.class));
            finish();
        }

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.account_bottom_navigation);

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Create a switch statement:
                switch (item.getItemId()){
                    //If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(AccountActivity.this,LandingActivity.class));
                        finish();
                        break;
                        //If the search icon is clicked,go to the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(AccountActivity.this,DiscountActivity.class));
                        finish();
                        break;
                        //If the account icon is clicked, stay on the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(AccountActivity.this,AccountActivity.class));
                        finish();
                        break;

                    default:
                }
                return true;
            }
        });
    }
}
