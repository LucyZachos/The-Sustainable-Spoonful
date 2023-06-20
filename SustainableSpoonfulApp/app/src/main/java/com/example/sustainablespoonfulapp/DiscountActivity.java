package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    //Create a list for the store images:
    private List<Bitmap> storeImages;

    BottomNavigationView bottom_nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        //Initialising the store discount images:
        storeImages = new ArrayList<>();

        //Fetch the store images from the database:
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        List<byte[]> imageBytes = databaseHelper.getAllStoreImages();

        //Convert the byte arrays to bitmaps:
        for(byte[] bytes : imageBytes){
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            storeImages.add(bitmap);
        }

        //Displaying the images in the ImageViews:
        ImageView store_image_view1 = findViewById(R.id.store_image_view1);
        ImageView store_image_view2 = findViewById(R.id.store_image_view2);
        ImageView store_image_view3 = findViewById(R.id.store_image_view3);
        ImageView store_image_view4 = findViewById(R.id.store_image_view4);

        if(storeImages.size()>0){
            store_image_view1.setImageBitmap(storeImages.get(0));
        }
        if(storeImages.size()>1){
            store_image_view2.setImageBitmap(storeImages.get(1));
        }
        if(storeImages.size()>2){
            store_image_view3.setImageBitmap(storeImages.get(2));
        }
        if(storeImages.size()>3){
            store_image_view4.setImageBitmap(storeImages.get(3));
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
