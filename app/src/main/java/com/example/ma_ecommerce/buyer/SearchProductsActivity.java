package com.example.ma_ecommerce.buyer;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.ma_ecommerce.viewHolder.ProductListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchProductsActivity extends AppCompatActivity {
    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;

    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        searchBtn = findViewById(R.id.search_button);
        inputText = findViewById(R.id.search_product_name);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = inputText.getText().toString();
                if (TextUtils.isEmpty(searchInput)) {
                    Toast.makeText(SearchProductsActivity.this, "Please fill the input search before ", Toast.LENGTH_SHORT).show();
                } else {
                    onStart();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Products> products = new ArrayList<Products>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/searchproduct.php?name=" + searchInput;
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
                        Log.e("dp", new Products(productDescription, name, price, productImage, id, productDate, productCategory, productState).toString());
                    } catch (Exception ex) {
                        Toast.makeText(SearchProductsActivity.this, "error", Toast.LENGTH_SHORT).show();
                        //Log.e("dp", ex.toString());
                    }

                }
                ProductListAdapter adapter = new ProductListAdapter(products, SearchProductsActivity.this, "Users");


                searchList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);
    }
}