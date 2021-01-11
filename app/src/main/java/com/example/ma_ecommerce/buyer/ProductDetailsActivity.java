package com.example.ma_ecommerce.buyer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private final String state = "Normal";
    private FloatingActionButton addToCart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, counterTotalPrice, productName, productDescription;
    private String productID = "";
    private ProgressDialog progressDialog;
    private Button addToCartButton;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        progressDialog = new ProgressDialog(this);
        counterTotalPrice = findViewById(R.id.product_counter_total_price);
        productImage = findViewById(R.id.prouduct_image_details);
        numberButton = findViewById(R.id.number_btn);
        productPrice = findViewById(R.id.prouduct_price_details);
        productName = findViewById(R.id.prouduct_name_details);
        addToCartButton = findViewById(R.id.add_product_to_cart_btn);
        productDescription = findViewById(R.id.prouduct_description_details);
        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addingToCartList();

            }
        });

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                double total = newValue * Double.parseDouble(productPrice.getText().toString());
                counterTotalPrice.setText("Total Price: " + total + " $");
            }
        });
    }


    private void addingToCartList() {
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/addToCart.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ProductDetailsActivity.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Log.e("dp", response);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map2 = new HashMap<>();
                uID = Prevalid.online.getID() + "";
                map2.put("uid", uID);
                map2.put("pid", getIntent().getStringExtra("pid"));
                map2.put("quantity", numberButton.getNumber());
                map2.put("price", (Double.parseDouble(getIntent().getStringExtra("price")) * Integer.parseInt(numberButton.getNumber())) + "");

                return map2;
            }
        };

        queue.add(request);
    }


    @Override
    protected void onStart() {
        super.onStart();
        String pid = getIntent().getStringExtra("pid");
        String pname = getIntent().getStringExtra("name");
        String pdesc = getIntent().getStringExtra("desc");
        String pimage = getIntent().getStringExtra("pimage");
        String price = getIntent().getStringExtra("price");
        productID = getIntent().getStringExtra("pid");

        productName.setText(pname);
        productPrice.setText(price);
        productDescription.setText(pdesc);
        Picasso.get().load(pimage).into(productImage);

    }


}