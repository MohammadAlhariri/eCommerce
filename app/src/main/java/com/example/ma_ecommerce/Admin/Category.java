package com.example.ma_ecommerce.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.seller.SellerAddNewProduct;

public class Category extends AppCompatActivity {
    private ImageView tshairts, sports, female_dresses, sweather, glasses, purses_bags,
            hats, shoess, headphoness, laptops, watches, mobiles;
    //private AppCompatButton logoutBtn, checkOrdersBtn ,maintainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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

        /*        logoutBtn = (AppCompatButton) findViewById(R.id.admin_logout_btn);
        checkOrdersBtn = (AppCompatButton) findViewById(R.id.check_orders_btn);
        maintainBtn = (AppCompatButton) findViewById(R.id.maintain_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Paper.book().destroy();
                finish();
            }
        });
        maintainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, HomeActivity.class);
               intent.putExtra("Admins","Admins");
                startActivity(intent);

            }
        });
        checkOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });*/ // Old declaration using Button

        tshairts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "tshairts");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "sports");
                startActivity(intent);
            }
        });
        female_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "female_dresses");
                startActivity(intent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "sweather");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "glasses");
                startActivity(intent);
            }
        });
        purses_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "purses_bags");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "hats");
                startActivity(intent);
            }
        });
        shoess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "shoess");
                startActivity(intent);
            }
        });
        headphoness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "headphoness");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "watches");
                startActivity(intent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, SellerAddNewProduct.class);
                intent.putExtra("category", "mobiles");
                startActivity(intent);
            }
        });

    }
}