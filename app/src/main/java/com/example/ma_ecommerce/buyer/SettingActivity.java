package com.example.ma_ecommerce.buyer;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private static final int gallery_photo = 1;
    private final String myUrl = "";
    private final String check = "";
    Bitmap bitmap;
    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView closeTextBtn, saveTextButton, profileChangeTextBtn;
    private Uri imageuri;
    private String checker = "";
    private Button securityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        profileImageView = findViewById(R.id.setting_profile_Image);
        fullNameEditText = findViewById(R.id.setting_full_name);
        userPhoneEditText = findViewById(R.id.setting_phone_Number);
        addressEditText = findViewById(R.id.setting_address);
        profileChangeTextBtn = findViewById(R.id.profile_change);
        closeTextBtn = findViewById(R.id.close);
        saveTextButton = findViewById(R.id.update);
        securityButton = findViewById(R.id.security_btn);
        securityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "settings");
                startActivity(intent);

            }
        });
        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userInfoSaved();

            }
        });
        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                openGallery();
            }
        });
        userInfoDisplay();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallery_photo && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            profileImageView.setImageURI(imageuri);
        }
    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString())) {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "Name is address.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())) {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }
    }

    private void userInfoDisplay() {


        String image = Prevalid.online.getImage();
        String name = Prevalid.online.getName();
        String phone = Prevalid.online.getPhone() + "";
        String address = Prevalid.online.getAddress();
        if (image != null) {
            Picasso.get().load(image).into(profileImageView);
        }
        fullNameEditText.setText(name);
        userPhoneEditText.setText(phone);
        addressEditText.setText(address);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, gallery_photo);

    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //---------------------------------------------
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/updateProfile.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(SettingActivity.this, "profile updated  successfully ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
//              Log.e("dp", response);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                Log.e("dp", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("name", fullNameEditText.getText().toString());
                userMap.put("address", addressEditText.getText().toString());
                userMap.put("phone", userPhoneEditText.getText().toString());
                userMap.put("image", imageString);
                userMap.put("uid", Prevalid.online.getID() + "");

                progressDialog.dismiss();
                return userMap;
            }
        };

        queue.add(request);
        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        Toast.makeText(SettingActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();


    }
}


