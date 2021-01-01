package com.example.ma_ecommerce.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.buyer.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AdminMaintainProduct extends AppCompatActivity {
    private Button applyChanges, deleteProduct;
    private EditText name, price, description;
    private ImageView imageView;
    private String productId = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);
        name = findViewById(R.id.maintain_product_mame);
        price = findViewById(R.id.maintain_product_price);
        description = findViewById(R.id.maintain_product_description);
        imageView = findViewById(R.id.maintain_product_image);
        applyChanges = findViewById(R.id.maintain_product_button);
        deleteProduct = findViewById(R.id.delete_product_button);
        productId = getIntent().getStringExtra("pid");
        progressDialog = new ProgressDialog(this);

        applyChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                applyChangesForProduct();
            }
        });
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] options = new CharSequence[]{
                        "yes",
                        "no"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMaintainProduct.this);
                builder.setTitle("Do you want to delete this product?");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {


                            final RequestQueue queue = Volley.newRequestQueue(AdminMaintainProduct.this);

                            String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/deleteProduct.php";


                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.e("dp", response);
                                    Toast.makeText(AdminMaintainProduct.this, "Product deleted successfully ", Toast.LENGTH_SHORT).show();


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(AdminMaintainProduct.this, error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> map2 = new HashMap<>();

                                    map2.put("pid", productId + "");

                                    return map2;
                                }
                            };

                            queue.add(request);
                            Intent intent = new Intent(AdminMaintainProduct.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        productId = getIntent().getStringExtra("pid");

        name.setText(getIntent().getStringExtra("name"));

        description.setText(getIntent().getStringExtra("desc"));
        price.setText(getIntent().getStringExtra("price"));

        Picasso.get().load(getIntent().getStringExtra("pimage")).into(imageView);
    }

    private void applyChangesForProduct() {
        progressDialog.setTitle("Please wait ... ");
        progressDialog.setMessage("Please wait ... ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();
        if (TextUtils.isEmpty(pName) || TextUtils.isEmpty(pDescription) || TextUtils.isEmpty(pPrice)) {
            Toast.makeText(this, "Please Fill all Information about product", Toast.LENGTH_SHORT).show();
        } else {
            final RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/updateProduct.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(AdminMaintainProduct.this, response, Toast.LENGTH_SHORT).show();
                    Log.e("db error", response);
                    Intent intent = new Intent(AdminMaintainProduct.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminMaintainProduct.this, error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("pid", productId);
                    map.put("name", name.getText().toString());
                    map.put("price", price.getText().toString());
                    map.put("description", description.getText().toString());

                    return map;
                }
            };

            queue.add(request);
        }
    }
}