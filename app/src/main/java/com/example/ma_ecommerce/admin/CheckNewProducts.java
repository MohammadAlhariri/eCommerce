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
import com.example.ma_ecommerce.buyer.HomeActivity;
import com.example.ma_ecommerce.model.Products;
import com.example.ma_ecommerce.viewHolder.CartItemAdapter;
import com.example.ma_ecommerce.viewHolder.ProductListAdapter;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckNewProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private TextView userName, userPhone, userAddress, orderDate, orderTotal;
//    private DatabaseReference databaseReference;
//    private DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_products);
        recyclerView = (RecyclerView) findViewById(R.id.approve_products_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog = new ProgressDialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        /*FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(databaseReference.orderByChild("productState").equalTo("Not Approved"), Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.productname.setText(model.getName());
                        holder.productdescreption.setText(model.getDescription());
                        holder.productprice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.productimage);

                        final  Products itemClick=model;
                        holder.itemView.setOnClickListener(new  View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID=itemClick.getPid();
                                CharSequence options[]=new CharSequence[] {
                                        "yes","no"
                                };
                                AlertDialog.Builder  builder=new AlertDialog.Builder(CheckNewProducts.this);
                                builder.setTitle("Do you want to Approved this product ?Are you sure ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0){
                                            changeProductState(productID);
                                        }
                                        else {


                                        }
                                    }
                                });
                                builder.show();


                                Intent intent;
                                if (type.equals("Admins")) {
                                    intent = new Intent(HomeActivity.this, AdminMaintainProduct.class);
                                } else {
                                    intent = new Intent(HomeActivity.this, ProductDetailsActivaty.class);
                                }
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent, false);
                        return new ProductViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void changeProductState(String productID) {
        databaseReference.child(productID).child("productState").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CheckNewProducts.this, "This item has been approved", Toast.LENGTH_SHORT).show();

            }
        });
    */ //   Using FireBaseDataRef

        getUnValidateProducts();


    }

    private void getUnValidateProducts() {
        ArrayList<Products>products=new ArrayList<>();
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
                        //Log.e("dp", new Products(productDescription, name, price, productImage, id, productDate, productCategory, productState).toString());
                    } catch (Exception ex) {
                        Toast.makeText(CheckNewProducts.this, "error", Toast.LENGTH_SHORT).show();
                        //Log.e("dp", ex.toString());
                    }

                }
                CartItemAdapter adapter = new CartItemAdapter(products, CheckNewProducts.this,"Admins");

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