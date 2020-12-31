package com.example.ma_ecommerce.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ma_ecommerce.R;

public class AdminMaintainProduct extends AppCompatActivity {
    private Button applyChanges, deleteProduct;
    private EditText name, price, description;
    private ImageView imageView;
    private String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);
        name = (EditText) findViewById(R.id.maintain_product_mame);
        price = (EditText) findViewById(R.id.maintain_product_price);
        description = (EditText) findViewById(R.id.maintain_product_description);
        imageView = (ImageView) findViewById(R.id.maintain_product_image);
        applyChanges = (Button) findViewById(R.id.maintain_product_button);
        deleteProduct = (Button) findViewById(R.id.delete_product_button);
        productId = getIntent().getStringExtra("pid");
    /*
        productRefrence = FirebaseDatabase.getInstance().getReference().child("Product").child(productId);
        displaySpecificProduct();


        applyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChangesForProduct();
            }
        });
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{
                        "yes",
                        "No"
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMaintainProduct.this);
                builder.setTitle("Do you want to delete this product?");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            productRefrence.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminMaintainProduct.this, "This Product is Removed ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminMaintainProduct.this, AdminHomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AdminMaintainProduct.this, "You ca not remove this item now ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            finish();
                        }
                    }
                });
                builder.show();

            }
        });

    }

    private void applyChangesForProduct() {
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();
        if (TextUtils.isEmpty(pName) || TextUtils.isEmpty(pDescription) || TextUtils.isEmpty(pPrice)) {
            Toast.makeText(this, "Please Fill all Information about product", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("pid", productId);
            map2.put("name", pName);
            map2.put("description", pDescription);
            map2.put("price", pPrice);
            productRefrence.updateChildren(map2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminMaintainProduct.this, "Changes apply successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminMaintainProduct.this, AdminHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AdminMaintainProduct.this, "Product does not updating", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }

    private void displaySpecificProduct() {
        productRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pName = snapshot.child("name").getValue().toString();
                    String pPrice = snapshot.child("price").getValue().toString();
                    String pDescription = snapshot.child("description").getValue().toString();
                    String pImage = snapshot.child("image").getValue().toString();
                    name.setText(pName);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    Picasso.get().load(pImage).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/ // Using FirebaseRef


    }
}