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


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalAmount ,msg1;
    private  Button nextProcessButton;
    private int totalOver=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        checkOrderReference();

        recyclerView=(RecyclerView)findViewById(R.id.cart_list);
        layoutManager=new LinearLayoutManager(this);
         nextProcessButton = (Button) findViewById(R.id.next_process_button);
        totalAmount=(TextView)findViewById(R.id.total);
        msg1=(TextView)findViewById(R.id.msg1);
        nextProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent=new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
               // intent.putExtra("total",totalOver+"");
                //startActivity(intent);
                finish();
            }
        });

    }

    private void checkOrderReference() {
        ArrayList<Orders> orders=new ArrayList<>();
        String sid = Paper.book().read(Prevalid.online.getID()+"");
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getLastOrder.php?sid=" + sid;
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    String orderState="Not Shipped";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject row = response.getJSONObject(i);
                            int orderId = row.getInt("orderID");
                            int userID = row.getInt("userID");
                             orderState = row.getString("orderState");
                        } catch (Exception ex) {
                            Toast.makeText(CartActivity.this, "error", Toast.LENGTH_SHORT).show();

                        }

                    }
                    String userName=Paper.book().read(Prevalid.online.getName());
                    if(orderState.equals("shipped")){
                        totalAmount.setText("Dear "+userName+"\nyour order was shipped successfully");
                        recyclerView.setVisibility(View.GONE);
                        msg1.setVisibility(View.VISIBLE);
                        nextProcessButton.setVisibility(View.INVISIBLE);
                        Toast.makeText(CartActivity.this, "You can purchase more products once you received your first shipped", Toast.LENGTH_SHORT).show();
                    }
                    else if(orderState.equals("not shipped")) {
                        totalAmount.setText("Dear "+userName+"\n your order  Not shipped Yet");
                        recyclerView.setVisibility(View.GONE);
                        msg1.setVisibility(View.VISIBLE);
                        nextProcessButton.setVisibility(View.INVISIBLE);
                        Toast.makeText(CartActivity.this, "You can purchase more products ", Toast.LENGTH_SHORT).show();

                    }
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

    }
}