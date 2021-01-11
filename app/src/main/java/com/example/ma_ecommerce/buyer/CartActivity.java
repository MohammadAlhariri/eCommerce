package com.example.ma_ecommerce.buyer;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.model.Orders;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.seller.SellerHomeActivity;
import com.example.ma_ecommerce.viewHolder.BankListAdapter;
import com.example.ma_ecommerce.viewHolder.CartItemAdapter;
import com.example.ma_ecommerce.viewHolder.ProductListAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalAmount, msg1;
    private Button nextProcessButton;
    private int totalOver = 0;
    String orderState = "";
    String userApproved="yes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        recyclerView = (RecyclerView) findViewById(R.id.cart_list);
        layoutManager = new LinearLayoutManager(this);
        nextProcessButton = findViewById(R.id.next_process_button);
        totalAmount = (TextView) findViewById(R.id.total);
        msg1 = (TextView) findViewById(R.id.msg1);
        recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(layoutManager);
        nextProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                 intent.putExtra("total",totalOver+"");
                startActivity(intent);
                finish();
            }
        });

    }

    private void checkOrderReference() {

        ArrayList<Orders> orders = new ArrayList<>();
        String uid = Prevalid.online.getID() + "";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getLastOrderNotShipped.php?uid=" + uid;
        Log.i("id",uid);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int orderId=0;
                    try {
                        JSONObject row = response.getJSONObject(0);
                        orderId = row.getInt("orderID");
                        int userID = row.getInt("userID");
                        orderState = row.getString("orderState");
                         userApproved=row.getString("adminApproved");
                    } catch (Exception ex) {
                        Toast.makeText(CartActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                if (userApproved.equals("Yes")) {
                    // totalAmount.setText("Dear " + userName + "\nyour order was shipped successfully");
                    //recyclerView.setVisibility(View.GONE);
                    //msg1.setVisibility(View.VISIBLE);
                    getProductsOfOrder(orderId+"");
                    Log.i("oid",orderId+"");
                    nextProcessButton.setClickable(false);
                    Toast.makeText(CartActivity.this, "You can purchase more products once you received your first shipped", Toast.LENGTH_SHORT).show();
                } else {
                    //totalAmount.setText("Dear " + userName + "\n your order  Not shipped Yet");
                    //recyclerView.setVisibility(View.GONE);
                    //msg1.setVisibility(View.VISIBLE);
                    nextProcessButton.setClickable(true);
                    getProductsOfOrder(orderId+"");
                    Toast.makeText(CartActivity.this, "You can purchase more products ", Toast.LENGTH_SHORT).show();

                }
                //String userName =Prevalid.online.getName();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);

    }


    @Override
    protected void onStart() {
        super.onStart();
       checkOrderReference();

    }
    public void getProductsOfOrder(String orderID){
        ArrayList<Products> products = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getOrderProductsByOrderID.php?uid=" + orderID;
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
                        String 	productDescription = row.getString("productDescription");
                        String 	productImage = row.getString("productImage");

                        products.add(new Products(poductName, price, productID, quantity,productDescription,productImage));
                    } catch (Exception ex) {
                        Toast.makeText(CartActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }

                }
                CartItemAdapter adapter = new CartItemAdapter(products, CartActivity.this,"Users");

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(request);
    }
}