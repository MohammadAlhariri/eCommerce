package com.example.ma_ecommerce.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.LoginActivity;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.buyer.HomeActivity;
import com.example.ma_ecommerce.model.Users;
import com.example.ma_ecommerce.prevalid.Prevalid;

import org.json.JSONArray;
import org.json.JSONObject;

import io.paperdb.Paper;

public class SellerLoginActivity extends AppCompatActivity {
    private EditText sellerEmail, sellerPassword;
    private Button sellerLogin;
    public  String parentname = "Sellers";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        //-----------------
        sellerLogin = findViewById(R.id.seller_login);
        sellerEmail = findViewById(R.id.seller_email_login);
        sellerPassword = findViewById(R.id.seller_password_login);
        progressDialog = new ProgressDialog(this);
        //-----------------
        Paper.init(this);
        sellerLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(SellerLoginActivity.this, "Here We go", Toast.LENGTH_SHORT).show();
                loginSeller();

            }
        });

    }

    private void loginSeller() {
        String pass = sellerPassword.getText().toString();
        String email = sellerEmail.getText().toString();
        Paper.book().write(Prevalid.userNumber, email);
        Paper.book().write(Prevalid.pass, pass);
        Paper.book().write(Prevalid.parentname, parentname);

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

            Toast.makeText(this, "Please fill all information needed", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Login to your Account ");
            progressDialog.setMessage("Please Wait ");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            String HTTP_SERVER_URL = "https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/sellerLogin.php?email=" + email + "&password=" + pass ;
            JsonArrayRequest jsArrRequest = new JsonArrayRequest(Request.Method.GET, HTTP_SERVER_URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Toast.makeText(SellerLoginActivity.this, "Welcome ,login successful ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    try {
                        JSONObject row = response.getJSONObject(0);
                        int id = row.getInt("sellerID");
                        String name = row.getString("sellerName");
                        int userphone = row.getInt("sellerPhone");
                        String email = row.getString("sellerEmail");
                        String address = row.getString("sellerAddress");
                        String image = row.getString("sellerImage");

                        String userpass = row.getString("sellerPassword");
                        Paper.book().write(Prevalid.id,id+"");
                        Prevalid.online = new Users(name, userpass, address, image, email, userphone, id);
                        Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                    } catch (Exception ex) {
                        Toast.makeText(SellerLoginActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SellerLoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(jsArrRequest);


        }
    }
}