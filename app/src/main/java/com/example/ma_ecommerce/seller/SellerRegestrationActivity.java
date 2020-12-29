package com.example.ma_ecommerce.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.MainActivity;
import com.example.ma_ecommerce.R;

import java.util.HashMap;
import java.util.Map;

public class SellerRegestrationActivity extends AppCompatActivity {
    private Button sellerAlreadyHave, sellerRegister;
    private EditText sellerName, sellerEmail, sellerPhone, sellerAddress, sellerPassword;
    // private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_regestration);
        //----------------
        sellerAlreadyHave = findViewById(R.id.seller_have_account);
        sellerRegister = findViewById(R.id.seller_register);
        sellerName = findViewById(R.id.seller_name);
        sellerEmail = findViewById(R.id.seller_email);
        sellerPhone = findViewById(R.id.seller_phone);
        sellerPassword = findViewById(R.id.seller_password);
        sellerAddress = findViewById(R.id.seller_address);
        //auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
//-------------------
        sellerAlreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerRegestrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });
        sellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerationSellr();
            }
        });

    }

    private void registerationSellr() {
        String name = sellerName.getText().toString();
        String pass = sellerPassword.getText().toString();
        String email = sellerEmail.getText().toString();
        String address = sellerAddress.getText().toString();
        String phone = sellerPhone.getText().toString();
        if (TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(pass) ||
                TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please fill all information needed", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Creating seller Account ");
            progressDialog.setMessage("Please Wait ");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/sellerRegister.php";


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(SellerRegestrationActivity.this, response, Toast.LENGTH_SHORT).show();

                    Log.e("db error",response);
                    Intent intent = new Intent(SellerRegestrationActivity.this, SellerLoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SellerRegestrationActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();

                    map.put("name", name);
                    map.put("phone", phone);
                    map.put("email", email);
                    map.put("address", address);
                    map.put("password", pass);
                    return map;
                }
            };

            queue.add(request);


        }
    }
}