package com.example.ma_ecommerce.viewHolder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.admin.AdminUserProducts;
import com.example.ma_ecommerce.buyer.ProductDetailsActivity;
import com.example.ma_ecommerce.model.Orders;
import com.example.ma_ecommerce.model.Products;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class orderListAdapter  extends RecyclerView.Adapter<orderListAdapter.orderViewHolder>{

    ArrayList<Orders> orders ;
    FragmentActivity activity;
    View selectBank;
    private ProgressDialog progressDialog;
    public orderListAdapter(ArrayList<Orders> orders, FragmentActivity activity) {
        this.orders = orders;
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);

    }


    @Override
    public orderListAdapter.orderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View orderListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent,false);
        orderListAdapter.orderViewHolder orderListViewHolder = new orderListAdapter.orderViewHolder(orderListLayout);
        return orderListViewHolder;
    }

    @Override
    public void onBindViewHolder(orderListAdapter.orderViewHolder holder, int position) {
        holder.userName.setText("Name : "+orders.get(position).getName());
        holder.userPhone.setText("Phone : "+orders.get(position).getPhone());
        holder.userAddress.setText("Shipping Address : "+orders.get(position).getCity()+" , "+orders.get(position).getAddress());
        holder.orderDate.setText("Order At  Date : "+orders.get(position).getDate());
        holder.orderTotal.setText("Total Amount : "+orders.get(position).getTotalAmount());
        holder.showAllOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String  uID=orders.get(position).getOrderID();
                Intent intent = new Intent(activity, AdminUserProducts.class);
                intent.putExtra("uid",uID);
                activity.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[]=new CharSequence[]{
                        "yes",
                        "No"
                };
                //String uID=getRef(position).getKey();

                AlertDialog.Builder builder =new AlertDialog.Builder(activity);
                builder.setTitle("Have you Shipped this order Products?");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            updateOrderState(orders.get(position).getOrderID());
                            Toast.makeText(activity, "This Order is Shipped ", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            activity.finish();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private void updateOrderState(String pid) {
        progressDialog.setTitle("Adding New Product  ");
        progressDialog.setMessage("Please Wait .....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final RequestQueue queue = Volley.newRequestQueue(activity);

        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/AdminShippedOrder.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("dp", response);
                //Toast.makeText(activity, "Product deleted from cart successfully ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map2 = new HashMap<>();

                map2.put("oid", pid+"");

                return map2;
            }
        };

        queue.add(request);
    }
    //
//
//    @Override
//    public int getItemCount() {
//        return products.size();
//    }
//
//
    public  class orderViewHolder extends RecyclerView.ViewHolder {
        public com.rey.material.widget.TextView userName, userPhone, userAddress, orderDate, orderTotal;
        public AppCompatButton showAllOrdersButton;

        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            showAllOrdersButton = (AppCompatButton) itemView.findViewById(R.id.show_all_products);
            userName = (com.rey.material.widget.TextView) itemView.findViewById(R.id.order_user_name);
            userPhone = (com.rey.material.widget.TextView) itemView.findViewById(R.id.order_phone_number);
            userAddress = (com.rey.material.widget.TextView) itemView.findViewById(R.id.order_address);
            orderDate = (com.rey.material.widget.TextView) itemView.findViewById(R.id.order_date);
            orderTotal = (TextView) itemView.findViewById(R.id.order_total_price);

            showAllOrdersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }


}
