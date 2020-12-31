package com.example.ma_ecommerce.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ma_ecommerce.MainActivity;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.buyer.HomeActivity;

import io.paperdb.Paper;

public class AdminHomeActivity extends AppCompatActivity {
    private Button logoutBtn, checkOrdersBtn ,maintainBtn,approveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        logoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        checkOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        maintainBtn = (Button) findViewById(R.id.maintain_btn);
        approveBtn = (Button) findViewById(R.id.approve_products_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Paper.book().destroy();
                finish();
            }
        });
        maintainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admins","Admins");
                startActivity(intent);

            }
        });
        checkOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });
        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, CheckNewProducts.class);
                startActivity(intent);
            }
        });
    }
}