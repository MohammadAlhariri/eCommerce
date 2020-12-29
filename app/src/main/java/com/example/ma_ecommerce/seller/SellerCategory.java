package com.example.ma_ecommerce.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ma_ecommerce.R;

public class SellerCategory extends AppCompatActivity {
    private ImageView tshairts, sports, female_dresses, sweather, glasses, purses_bags,
            hats, shoess, headphoness, laptops, watches, mobiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category);
        //-----------------------
        tshairts = (ImageView) findViewById(R.id.tshairts);
        sports = (ImageView) findViewById(R.id.sports);
        female_dresses = (ImageView) findViewById(R.id.female_dresses);
        sweather = (ImageView) findViewById(R.id.sweather);
        glasses = (ImageView) findViewById(R.id.glasses);
        purses_bags = (ImageView) findViewById(R.id.purses_bags);
        hats = (ImageView) findViewById(R.id.hats);
        shoess = (ImageView) findViewById(R.id.shoess);
        headphoness = (ImageView) findViewById(R.id.headphoness);
        laptops = (ImageView) findViewById(R.id.laptops);
        watches = (ImageView) findViewById(R.id.watches);
        mobiles = (ImageView) findViewById(R.id.mobiles);
        tshairts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "tshairts");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "sports");
                startActivity(intent);
            }
        });
        female_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "female_dresses");
                startActivity(intent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "sweather");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "glasses");
                startActivity(intent);
            }
        });
        purses_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "purses_bags");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "hats");
                startActivity(intent);
            }
        });
        shoess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "shoess");
                startActivity(intent);
            }
        });
        headphoness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "headphoness");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "watches");
                startActivity(intent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerCategory.this, SellerAddNewProduct.class);
                intent.putExtra("category", "mobiles");
                startActivity(intent);
            }
        });

    }
}