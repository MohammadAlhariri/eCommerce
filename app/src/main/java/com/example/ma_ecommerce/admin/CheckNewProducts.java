package com.example.ma_ecommerce.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.viewHolder.CartItemAdapter;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckNewProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private TextView userName, userPhone, userAddress, orderDate, orderTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_products);
        recyclerView = findViewById(R.id.approve_products_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getUnValidateProducts();
    }

    private void getUnValidateProducts() {
        ArrayList<Products> products = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getUnvalidProducts.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject row = response.getJSONObject(i);
                        int id = row.getInt("productID");
                        String name = row.getString("productName");
                        int sellerID = row.getInt("sellerID");
                        double price = row.getDouble("productPrice");
                        String productCategory = row.getString("productCategory");
                        String productImage = row.getString("productImage");
                        String productState = row.getString("productState");
                        String productDate = row.getString("productDate");
                        String productDescription = row.getString("productDescription");

                        products.add(new Products(productDescription, name, price, productImage, id, productDate, productCategory, productState));
                    } catch (Exception ex) {
                        Toast.makeText(CheckNewProducts.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }
                CartItemAdapter adapter = new CartItemAdapter(products, CheckNewProducts.this, "Admins");

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dp", error.toString());

            }
        });

        queue.add(request);
    }

}