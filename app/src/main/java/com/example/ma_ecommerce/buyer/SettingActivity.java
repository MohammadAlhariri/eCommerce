package com.example.ma_ecommerce.buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView closeTextBtn, saveTextButton, profileChangeTextBtn;
    private Uri imageuri;
    private String myUrl = "";
    private String check = "";
    private static final int gallery_photo = 1;
    private String checker = "";
    private Button securityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        profileImageView = (CircleImageView) findViewById(R.id.setting_profile_Image);
        fullNameEditText = (EditText) findViewById(R.id.setting_full_name);
        userPhoneEditText = (EditText) findViewById(R.id.setting_phone_Number);
        addressEditText = (EditText) findViewById(R.id.setting_address);
        profileChangeTextBtn = (TextView) findViewById(R.id.profile_change);
        closeTextBtn = (TextView) findViewById(R.id.close);
        saveTextButton = (TextView) findViewById(R.id.update);
        securityButton = findViewById(R.id.security_btn);

        // userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, addressEditText);
        securityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=new Intent(SettingActivity.this, ResetPasswordActivity.class);
                //intent.putExtra("check","settings");
                // startActivity(intent);

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
                if (checker.equals("clicked")) {
                    userInfoSaved();
                } else {
                    // updateOnlyUserInfo();
                }
            }
        });
        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

//                CropImage.activity(imageUri)
//                        .setAspectRatio(1, 1)
//                        .start(Setting.this);
                openGallery();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallery_photo && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
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

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {


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


        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameEditText.getText().toString());
        userMap.put("address", addressEditText.getText().toString());
        userMap.put("phoneOrder", userPhoneEditText.getText().toString());
        userMap.put("image", myUrl);

        progressDialog.dismiss();

        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
        Toast.makeText(SettingActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();


    }
}


