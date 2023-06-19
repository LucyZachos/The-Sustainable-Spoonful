package com.example.sustainablespoonfulapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Create the main page when opening the application:

        //Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false); //Set is logged in to false:

        //If the customer is logged in, redirect to the home page:
        if(isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LandingActivity.class);
            startActivity(intent);
            finish();
        }

        /*Navigating to the Registration Page After Clicking the Register Button*/
        Button registerButton=findViewById(R.id.home_register_button);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class); //Redirect the customer to the registration page:
                startActivity(intent);
            }
        });

        /*Navigating to the Login Page After Clicking the Login Button*/
        Button loginButton=findViewById(R.id.home_login_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,LoginActivity.class); //Redirect the customer to the login page:
                startActivity(intent);
            }
        });
    }
}