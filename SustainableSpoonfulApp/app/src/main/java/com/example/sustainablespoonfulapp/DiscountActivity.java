package com.example.sustainablespoonfulapp;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DiscountActivity extends AppCompatActivity {

    CardView cardPicknpay;
    CardView cardCheckers;
    CardView cardWoolworths;
    CardView cardFoodLoversMarket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        cardPicknpay = findViewById(R.id.cardPicknpay);
        cardCheckers = findViewById(R.id.cardCheckers);
        cardWoolworths = findViewById(R.id.cardWoolworths);
        cardFoodLoversMarket = findViewById(R.id.cardFoodLoversMarket);

        cardPicknpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Pick n Pay Clicked");
            }
        });
        cardCheckers .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Checkers Clicked");
            }
        });
        cardWoolworths .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Woolworths Clicked");
            }
        });
        cardFoodLoversMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Food Loavers Market Clicked");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(DiscountActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
