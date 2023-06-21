package com.example.sustainablespoonfulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class ProductViewActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav_bar;

    private String getRetailerName(int retailerId) {
        // Retrieve the retailer name based on the retailer ID from the database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getRetailerNameById(retailerId);
    }

    private List<DiscountedProduct> getProductsByRetailer(int retailerId) {
        // Retrieve the list of products for the specified retailer from the database or an API
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getDiscountedProductsByRetailerId(retailerId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        // Retrieve the retailer ID passed from the previous activity
        int retailerId = getIntent().getIntExtra("retailerId", -1);

        // Set the title of the activity based on the retailer
        String retailerName = getRetailerName(retailerId);
        setTitle(retailerName + " Discounted Items");

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.discounted);

        ProductAdapter productAdapter = new ProductAdapter(getProductsByRetailer(retailerId), this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
