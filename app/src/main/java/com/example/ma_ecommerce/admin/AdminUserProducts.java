package com.example.ma_ecommerce.admin;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminUserProducts extends AppCompatActivity {
    private RecyclerView productList;
    private RecyclerView.LayoutManager layoutManager;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        productList = findViewById(R.id.products_list);
        layoutManager = new LinearLayoutManager(this);
        userID = getIntent().getStringExtra("uid");
        productList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayList<Products> products = new ArrayList<>();
        String uid = getIntent().getStringExtra("uid");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getOrderProductsByOrderID.php?uid=" + uid;
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject row = response.getJSONObject(i);
                        int productID = row.getInt("productID");
                        int quantity = row.getInt("quantity");
                        String poductName = row.getString("productName");
                        double price = row.getDouble("productPrice");
                        String productDescription = row.getString("productDescription");
                        String productImage = row.getString("productImage");
                        products.add(new Products(poductName, price, productID, quantity, productDescription, productImage));
                    } catch (Exception ex) {
                        Toast.makeText(AdminUserProducts.this, "error", Toast.LENGTH_SHORT).show();

                    }

                }
                CartItemAdapter adapter = new CartItemAdapter(products, AdminUserProducts.this, "Users");

                adapter.notifyDataSetChanged();
                productList.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);

    }
}