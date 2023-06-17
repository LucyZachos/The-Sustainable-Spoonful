package com.example.sustainablespoonfulapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class AccountActivity extends AppCompatActivity{
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
    }
}
