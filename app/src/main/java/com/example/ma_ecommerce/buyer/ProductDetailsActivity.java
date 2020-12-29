package com.example.ma_ecommerce.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.ma_ecommerce.seller.SellerAddNewProduct;
import com.example.ma_ecommerce.seller.SellerHomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity {
    private FloatingActionButton addToCart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productName, productDescription;
    private String productID="",state="Normal";
    private ProgressDialog progressDialog;
    private Button addToCartButton;
    private String  uID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        progressDialog = new ProgressDialog(this);

        productImage = (ImageView) findViewById(R.id.prouduct_image_details);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productPrice = (TextView) findViewById(R.id.prouduct_price_details);
        productName = (TextView) findViewById(R.id.prouduct_name_details);
        addToCartButton = (Button) findViewById(R.id.add_product_to_cart_btn);
        productDescription = (TextView) findViewById(R.id.prouduct_description_details);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                if(state.equals("order placed")||state.equals("order shipped")){
//                    Toast.makeText(ProductDetailsActivity.this, "You can purchase more products once your first shipped arrived", Toast.LENGTH_SHORT).show();
//
//                }
//                else {
                    addingToCartList();
              //  }
            }
        });
    }

    private void addingToCartList() {
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/addToCart.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ProductDetailsActivity.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                uID = Paper.book().read(Prevalid.id).toString();
                map2.put("uid", uID);
                map2.put("pid", getIntent().getStringExtra("pid"));
                map2.put("quantity", numberButton.getNumber());
                map2.put("price",(Double.parseDouble(getIntent().getStringExtra("price"))*Integer.parseInt(numberButton.getNumber()))+"");

                return map2;
            }
        };

        queue.add(request);
    }




    @Override
    protected void onStart() {
        super.onStart();
        String pid=getIntent().getStringExtra("pid");
        String pname=getIntent().getStringExtra("name");
        String pdesc=getIntent().getStringExtra("desc");
        String pimage=getIntent().getStringExtra("pimage");
        String price=getIntent().getStringExtra("price");
        productID = getIntent().getStringExtra("pid");

        productName.setText(pname);
        productPrice.setText(price);
        productDescription.setText(pdesc);
        Picasso.get().load(pimage).into(productImage);

    }
}