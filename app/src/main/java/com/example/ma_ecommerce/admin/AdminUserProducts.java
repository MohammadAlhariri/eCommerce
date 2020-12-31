package com.example.ma_ecommerce.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ma_ecommerce.R;

public class AdminUserProducts extends AppCompatActivity {
    private RecyclerView productList;
    private RecyclerView.LayoutManager layoutManager;
    // private DatabaseReference cartReference;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        productList = findViewById(R.id.products_list);
        layoutManager = new LinearLayoutManager(this);
        userID = getIntent().getStringExtra("uid");
        /*        cartReference = FirebaseDatabase.getInstance().getReference()
                .child("Cart List")
                .child("Admin View").child(userID).child("products");
        productList.setLayoutManager(layoutManager);*/ //cartReference
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartReference, Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.txtProductQuantity.setText(model.getQuantity());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText(model.getPrice());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        productList.setAdapter(adapter);
        adapter.startListening();

    */ // Using Firebase


    }
}