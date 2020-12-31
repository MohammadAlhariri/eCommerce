package com.example.ma_ecommerce.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.buyer.CartActivity;
import com.example.ma_ecommerce.model.Orders;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView userName, userPhone, userAddress, orderDate, orderTotal;
    // private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        recyclerView = (RecyclerView) findViewById(R.id.order_list);
        layoutManager = new LinearLayoutManager(this);
        // databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrder();
        /*FirebaseRecyclerOptions<Orders> option =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(databaseReference, Orders.class).build();
        FirebaseRecyclerAdapter<Orders, OrdersViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(option) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull Orders model) {
                holder.userName.setText("Name : "+model.getName());
                holder.userPhone.setText("Phone : "+model.getPhone());
                holder.userAddress.setText("Shipping Address : "+model.getCity()+" , "+model.getAddress());
                holder.orderDate.setText("Order At  Date : "+model.getDate()+" | "+model.getTime());
                holder.orderTotal.setText("Total Amount : "+model.getTotalAmount());
                holder.showAllOrdersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uID=getRef(position).getKey();
                        Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProducts.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "yes",
                                "No"
                        };
                        String uID=getRef(position).getKey();

                        AlertDialog.Builder builder =new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Have you Shipped this order Products?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    databaseReference.child(uID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(AdminNewOrdersActivity.this, "This Order is Removed ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                return new OrdersViewHolder(view);

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    */
    }

    private void getOrder() {
        ArrayList<Orders>orders=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getLastOrder.php?";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject row = response.getJSONObject(i);
                        int orderId = row.getInt("orderID");
                        String customerName=row.getString("customerName");
                        double total=row.getDouble("orderTotal");
                        int userID = row.getInt("userID");
                        String orderDate = row.getString("orderDate");

                        String city=row.getString("customerCity");
                        String address=row.getString("customerAddress");
                        int phone=row.getInt("customerPhone");
                        orders.add(new Orders(address,city,orderDate,phone+"",customerName,total+""));
                    } catch (Exception ex) {
                        Toast.makeText(AdminNewOrdersActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }

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

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userPhone, userAddress, orderDate, orderTotal;
        public AppCompatButton showAllOrdersButton;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            showAllOrdersButton = (AppCompatButton) itemView.findViewById(R.id.show_all_products);
            userName = (TextView) itemView.findViewById(R.id.order_user_name);
            userPhone = (TextView) itemView.findViewById(R.id.order_phone_number);
            userAddress = (TextView) itemView.findViewById(R.id.order_address);
            orderDate = (TextView) itemView.findViewById(R.id.order_date);
            orderTotal = (TextView) itemView.findViewById(R.id.order_total_price);

            showAllOrdersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }

}