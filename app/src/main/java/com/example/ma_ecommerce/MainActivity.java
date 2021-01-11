package com.example.ma_ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.buyer.HomeActivity;
import com.example.ma_ecommerce.model.Users;
import com.example.ma_ecommerce.prevalid.Prevalid;
import com.example.ma_ecommerce.seller.SellerRegestrationActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button user_login, user_join;
    private ProgressDialog progressDialog;
    private TextView seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_login = (Button) findViewById(R.id.user_admin_login);
        user_join = (Button) findViewById(R.id.user_join);
        seller = findViewById(R.id.be_seller);
        Paper.init(this);
        progressDialog = new ProgressDialog(this);

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SellerRegestrationActivity.class);
                startActivity(intent);
            }
        });

        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        user_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistertionActivity.class);
                startActivity(intent);
            }
        });

        String phone = Paper.book().read(Prevalid.userNumber);
        String pass = Paper.book().read(Prevalid.pass);
        String parent = Paper.book().read(Prevalid.parentname);

//        if (Prevalid.online != null) {
        if (phone != "" && pass != "" && parent != "") {
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass)) {
                allow_access(phone, pass, parent);
                progressDialog.setTitle("Already Login");
                progressDialog.setMessage("Please Wait ....... ");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        }
//        }

    }


    public void allow_access(String phone, String password, String parent) {


        String HTTP_SERVER_URL = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/login.php?phone=" + phone + "&password=" + password + "&parent=" + parent;
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, HTTP_SERVER_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if (parent.equals("Users") || parent.equals("Admins")) {
                            progressDialog.dismiss();
                            try {
                                JSONObject row = response.getJSONObject(0);
                                int id = row.getInt("userID");
                                String name = row.getString("userName");
                                int userphone = row.getInt("userPhone");
                                String email = row.getString("userEmail");
                                String address = row.getString("userAddress");
                                String image = row.getString("userImage");
                                String answer1 = row.getString("userAnswer1");
                                String answer2 = row.getString("userAnswer2");
                                String userpass = row.getString("userPassword");
                                Users users = null;
                                if (userpass == null) {
                                    Toast.makeText(MainActivity.this, "The password is incorrect", Toast.LENGTH_SHORT).show();
                                } else {
                                    users = new Users(name, userpass, address, image, email, answer1, answer2, userphone, id);
                                    Prevalid.online = users;
                                }
                            } catch (Exception ex) {
                                Toast.makeText(MainActivity.this, "remember me log in error", Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(MainActivity.this, "Welcome ... ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } /*else if (parent.equals("Sellers")) {
                            Toast.makeText(MainActivity.this, "Welcome, login successful ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            try {

                                JSONObject row = response.getJSONObject(0);
                                int id = row.getInt("userID");
                                String name = row.getString("userName");
                                int userphone = row.getInt("userPhone");
                                String email = row.getString("userEmail");
                                String address = row.getString("userAddress");
                                String image = row.getString("userImage");
                                String answer1 = row.getString("userAnswer1");
                                String answer2 = row.getString("userAnswer2");
                                String userpass = row.getString("userPassword");
                                Prevalid.online = new Users(name, userpass, address, image, email, userphone, id);
                            } catch (Exception ex) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(MainActivity.this, SellerHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }*/ // seller remember me
                        else {
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }


                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsArrRequest);
    }
}
