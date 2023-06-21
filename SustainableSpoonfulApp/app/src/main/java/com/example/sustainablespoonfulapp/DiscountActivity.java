package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    // Create a list for the store images:
    private List<Bitmap> storeImages;

    // TextViews used to display the number of discounts that are available on the page:
    TextView picknpayTextView;
    TextView checkersTextView;
    TextView woolworthsTextView;
    TextView foodloversmarketTextView;
    BottomNavigationView bottom_nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        // Initializing the store discount images:
        storeImages = new ArrayList<>();

        // Fetch the store images from the database:
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<byte[]> imageBytes = databaseHelper.getAllStoreImages();

        // Convert the byte arrays to bitmaps:
        for (byte[] bytes : imageBytes) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            storeImages.add(bitmap);
        }

        // Displaying the images in the ImageViews:
        ImageView store_image_view1 = findViewById(R.id.store_image_view1);
        ImageView store_image_view2 = findViewById(R.id.store_image_view2);
        ImageView store_image_view3 = findViewById(R.id.store_image_view3);
        ImageView store_image_view4 = findViewById(R.id.store_image_view4);

        if (storeImages.size() > 0) { // Display PicknPay logo:
            store_image_view1.setImageBitmap(storeImages.get(0));
        }
        if (storeImages.size() > 1) { // Display Woolworths logo:
            store_image_view2.setImageBitmap(storeImages.get(1));
        }
        if (storeImages.size() > 2) { // Display Checkers logo:
            store_image_view3.setImageBitmap(storeImages.get(2));
        }
        if (storeImages.size() > 3) { // Display Foodloversmarket logo:
            store_image_view4.setImageBitmap(storeImages.get(3));
        }

        // This will be used to find the location of the TextViews on the discounts page:
        picknpayTextView = findViewById(R.id.picknpay_card_text);
        woolworthsTextView = findViewById(R.id.woolworths_card_text);
        checkersTextView = findViewById(R.id.checkers_card_text);
        foodloversmarketTextView = findViewById(R.id.foodloversmarket_card_text);

        // Create a new database helper:
        DatabaseHelper dbhelper = new DatabaseHelper(this);

        // Set the retailer ids for each store that will be passed into the sql count discounted products query:
        int picknpayId = 1, woolworthsId = 2, checkersId = 3, foodloversID = 4;

        // Set the text for each TextView by calling the getDiscountProductCount function and passing in the retailer id:
        picknpayTextView.setText(dbhelper.getDiscountedProductCount(picknpayId));
        woolworthsTextView.setText(dbhelper.getDiscountedProductCount(woolworthsId));
        checkersTextView.setText(dbhelper.getDiscountedProductCount(checkersId));
        foodloversmarketTextView.setText(dbhelper.getDiscountedProductCount(foodloversID));

        // Set click listeners for the card views
        CardView cardView1 = findViewById(R.id.cardPicknpay);
        CardView cardView2 = findViewById(R.id.cardCheckers);
        CardView cardView3 = findViewById(R.id.cardWoolworths);
        CardView cardView4 = findViewById(R.id.cardFoodLoversMarket);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductViewActivity(1);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductViewActivity(2);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductViewActivity(3);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProductViewActivity(4);
            }
        });

        bottom_nav_bar = findViewById(R.id.bottom_nav_bar);
        bottom_nav_bar.setSelectedItemId(R.id.search_bottom_navigation); // Set the search icon to selected when on this page:

        bottom_nav_bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Create a switch statement:
                switch (item.getItemId()) {
                    // If the home icon is clicked, go to the home page:
                    case R.id.home_bottom_navigation:
                        startActivity(new Intent(DiscountActivity.this, LandingActivity.class)); // Redirect the customer to the home page:
                        return true;
                    // If the search icon is clicked, stay on the search discounts page:
                    case R.id.search_bottom_navigation:
                        startActivity(new Intent(DiscountActivity.this, DiscountActivity.class)); // Stay on the search discount page:
                        return true;
                    // If the account icon is clicked, go to the account page:
                    case R.id.account_bottom_navigation:
                        startActivity(new Intent(DiscountActivity.this, AccountActivity.class)); // Redirect the customer to the account page:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void startProductViewActivity(int retailerId) {
        // Start the ProductViewActivity passing the retailer ID as an extra
        Intent intent = new Intent(DiscountActivity.this, ProductViewActivity.class);
        intent.putExtra("retailerId", retailerId);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Checking if the customer is logged in:
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false); // Set is logged in to true:

        // If the customer is not logged in, redirect to the home page:
        if (!isLoggedIn) {
            Toast.makeText(DiscountActivity.this, "Please log in to continue!", Toast.LENGTH_SHORT).show(); // Display a message to the customer asking them to log in:
            startActivity(new Intent(DiscountActivity.this, MainActivity.class)); // Redirect to the main page of the application:
            finish(); // Finishing the current activity so that customers' cannot go back to it when pressing the back button:
        }
    }
}
