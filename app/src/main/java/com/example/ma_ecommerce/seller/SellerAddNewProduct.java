package com.example.ma_ecommerce.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.viewHolder.VolleyMultibleRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class SellerAddNewProduct extends AppCompatActivity {
    private String categoryName, description, price, name,
            saveCurrentDate, saveCurrentTime, randomKey, downloadImage;
    private Button add_new_product;
    private ImageView p_photo;
    private static final int gallery_photo = 1;
    private Uri imageuri;
    private EditText p_name, p_description, p_price;

    private ProgressDialog progressDialog;
    //----------------------

    int PICK_IMAGE_REQUEST = 111;
    //String URL = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/upload/";
    Bitmap bitmap;
    //-----------------
    private String sellerName, sellerAddress, sellerEmail, sellerID, sellerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product);
        add_new_product = (Button) findViewById(R.id.add_new_prouduct_button);
        p_name = (EditText) findViewById(R.id.product_name);
        p_description = (EditText) findViewById(R.id.product_description);
        p_price = (EditText) findViewById(R.id.product_price);
        p_photo = (ImageView) findViewById(R.id.select_product_image);
        progressDialog = new ProgressDialog(this);
        p_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateProductData();
            }
        });


    }

    private void ValidateProductData() {
        description = p_description.getText().toString();
        name = p_name.getText().toString();
        price = p_price.getText().toString();
        //if (imageuri == null) {
        //  Toast.makeText(this, "please Enter the image of this product", Toast.LENGTH_SHORT).show();
        //} else if (TextUtils.isEmpty(description) || TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
        //  Toast.makeText(this, "please fill all mandatory fields", Toast.LENGTH_SHORT).show();
        //} else {
        storeProduct();
        //}
    }

    private void storeProduct() {
        progressDialog.setTitle("Adding New Product  ");
        progressDialog.setMessage("Please Wait .....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        saveProductnfo();
    }

    private void saveProductnfo() {

//        databaseReference.child(randomKey).updateChildren(map2).addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//
//
//                Toast.makeText(SellerAddNewProduct.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                Intent intent = new Intent(SellerAddNewProduct.this, SellerHomeActivity.class);
//                startActivity(intent);
//            }
//            else {
//                progressDialog.dismiss();
//
//                Toast.makeText(SellerAddNewProduct.this, "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
        //---------------------------
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //---------------------------------------------
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/addproduct.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(SellerAddNewProduct.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(SellerAddNewProduct.this, SellerHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SellerAddNewProduct.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map2 = new HashMap<>();
                sellerID = Paper.book().read(Prevalid.id);
                categoryName = getIntent().getStringExtra("category");
                map2.put("name", name);
                map2.put("description", description);
                map2.put("price", price);
                map2.put("image", imageString);
                //map2.put("image", downloadImage);
                map2.put("category", categoryName);
                map2.put("sellerID", sellerID);
                return map2;
            }
        };

        queue.add(request);
    }


    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Log.e("image",filePath.getPath());
                //Setting image to ImageView
                p_photo.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("image",e.toString());
            }
        }
    }
}





