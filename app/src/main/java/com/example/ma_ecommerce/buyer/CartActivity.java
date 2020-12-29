package com.example.ma_ecommerce.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button nextProcessButton;
    private RecyclerView.LayoutManager layoutManager;
    private TextView totalAmount ,msg1;
    private int totalOver=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        checkOrderReference();

        recyclerView=(RecyclerView)findViewById(R.id.cart_list);
        layoutManager=new LinearLayoutManager(this);
        nextProcessButton=(Button)findViewById(R.id.next_process_button) ;
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
        String sid = Paper.book().read(Prevalid.id);
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/getPoductsOfSeller.php?sid=" + sid;
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

                            orders.add(new Products(productDescription, name, price, productImage, id, productDate, productCategory, productState));
                            Log.e("dp", new Products(productDescription, name, price, productImage, id, productDate, productCategory, productState).toString());
                        } catch (Exception ex) {
                            Toast.makeText(SellerHomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                            //Log.e("dp", ex.toString());
                        }

                    }
                    BankListAdapter adapter=new BankListAdapter(products,SellerHomeActivity.this);

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


    @Override
    protected void onStart() {
        super.onStart();

    }
}