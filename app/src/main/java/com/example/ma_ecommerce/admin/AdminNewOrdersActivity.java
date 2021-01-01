package com.example.ma_ecommerce.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.model.Orders;
import com.example.ma_ecommerce.viewHolder.orderListAdapter;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView userName, userPhone, userAddress, orderDate, orderTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        recyclerView = findViewById(R.id.order_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrder();
    }

    private void getOrder() {
        ArrayList<Orders> orders = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getOrders.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject row = response.getJSONObject(i);
                        int orderId = row.getInt("orderID");
                        String customerName = row.getString("customerName");
                        double total = row.getDouble("orderTotal");
                        int userID = row.getInt("userID");
                        String orderDate = row.getString("orderDate");
                        String city = row.getString("customerCity");
                        String address = row.getString("customerAddress");
                        int phone = row.getInt("customerPhone");
                        orders.add(new Orders(address, city, orderDate, phone + "", customerName, total + "", orderId + ""));
                    } catch (Exception ex) {
                        Toast.makeText(AdminNewOrdersActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }

                }
                orderListAdapter adapter = new orderListAdapter(orders, AdminNewOrdersActivity.this);
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

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userPhone, userAddress, orderDate, orderTotal;
        public AppCompatButton showAllOrdersButton;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            showAllOrdersButton = itemView.findViewById(R.id.show_all_products);
            userName = itemView.findViewById(R.id.order_user_name);
            userPhone = itemView.findViewById(R.id.order_phone_number);
            userAddress = itemView.findViewById(R.id.order_address);
            orderDate = itemView.findViewById(R.id.order_date);
            orderTotal = itemView.findViewById(R.id.order_total_price);

            showAllOrdersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }

}