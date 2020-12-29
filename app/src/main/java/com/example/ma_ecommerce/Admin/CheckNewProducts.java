package com.example.ma_ecommerce.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ma_ecommerce.R;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

public class CheckNewProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView userName, userPhone, userAddress, orderDate, orderTotal;
//    private DatabaseReference databaseReference;
//    private DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_products);
        recyclerView = (RecyclerView) findViewById(R.id.approve_products_list);
        layoutManager = new LinearLayoutManager(this);
        // databaseReference = FirebaseDatabase.getInstance().getReference().child("Product");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

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
    */ //   Useing FireBaseDataRef
    }
}