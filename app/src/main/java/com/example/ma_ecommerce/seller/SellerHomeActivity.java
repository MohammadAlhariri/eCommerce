package com.example.ma_ecommerce.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.MainActivity;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.viewHolder.BankListAdapter;
import com.example.ma_ecommerce.viewHolder.SellerItemHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SellerHomeActivity extends AppCompatActivity {
    private ArrayList<Products> products;
    private TextView mTextMessage;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView name, price, description;
    private ImageView imageView;
    private String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        //----------
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Paper.init(this);
        recyclerView = (RecyclerView) findViewById(R.id.seller_home_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//
//        name = findViewById(R.id.maintain_product_mame);
//        price = findViewById(R.id.maintain_product_price);
//        description = findViewById(R.id.maintain_product_description);
//        imageView = findViewById(R.id.maintain_product_image);
        //------------------
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_logout)
                .build();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        View navigation_logout = findViewById(R.id.navigation_logout);
        navigation_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent intent = new Intent(SellerHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });
        View navigation_add = findViewById(R.id.navigation_add);
        navigation_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SellerHomeActivity.this, SellerCategory.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        products = new ArrayList<Products>();
        fillList();
        //getProducts();
    }

    String sid = Paper.book().read(Prevalid.id);

    private void fillList() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/getPoductsOfSeller.php?sid=" + sid;
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

    /*    private void getProducts() {

       adapter = new RecyclerView.Adapter<SellerItemHolder>() {
            @NonNull
            @Override
            public SellerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                return new SellerItemHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull SellerItemHolder holder, int position) {

                holder.sellerproductname.setText(products.get(position).getName());
                holder.sellerproductdescreption.setText(products.get(position).getDescription());
                holder.sellerproductprice.setText("Price = " + products.get(position).getPrice() + "$");
                holder.sellerproductstate.setText(products.get(position).getProductState());

                //Picasso.get().load(model.getImage()).into(holder.sellerproductimage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CharSequence options[] = new CharSequence[]{
                                "yes",
                                "No"
                        };
                        // String uID = getRef(position).getKey();

                        AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                        builder.setTitle("this product will be removed ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    //deleteProduct(model.getPid());
                                } else {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };

        recyclerView.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mclweb2020.000webhostapp.com/mob/getProducts.php";
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
                        String productDate = row("productDate");
                        String productDescription = row.getString("productDescription");
                        products.add(new Products(id, name, quantity, price, category));


                    } catch (Exception ex) {
                        Toast.makeText(SellerHomeActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, null);

        queue.add(request);


    }*/
}