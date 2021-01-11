package com.example.ma_ecommerce.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.ma_ecommerce.LoginActivity;
import com.example.ma_ecommerce.R;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText pass1, pass2;
    private Button changeButton;
    private TextView pageNumber, notsame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        pass1 = findViewById(R.id.password_1);
        pass2 = findViewById(R.id.password_2);
        changeButton = findViewById(R.id.change_password_btn);
        notsame = findViewById(R.id.notsame);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer1 = pass1.getText().toString().toLowerCase();
                String answer2 = pass2.getText().toString().toLowerCase();
                String uid = getIntent().getStringExtra("uid");
                if (TextUtils.isEmpty(answer1) || TextUtils.isEmpty(answer2)) {
                    Toast.makeText(ChangePasswordActivity.this, "Please fill all required input ", Toast.LENGTH_SHORT).show();
                    notsame.setVisibility(View.VISIBLE);

                } else if (!answer1.equals(answer2)) {
                    notsame.setText("Please sure the two space shouldbe the same ");
                    Toast.makeText(ChangePasswordActivity.this, "Please try again  ", Toast.LENGTH_SHORT).show();
                    notsame.setVisibility(View.VISIBLE);
                } else {
                    final RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);

                    String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/updatePassword.php";


                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ChangePasswordActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap<String, String> usermap = new HashMap<>();
                            usermap.put("password", answer1);
                            usermap.put("uid", uid);

                            return usermap;
                        }
                    };

                    queue.add(request);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}