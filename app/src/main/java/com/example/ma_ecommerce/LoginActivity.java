package com.example.ma_ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.admin.AdminHomeActivity;
import com.example.ma_ecommerce.buyer.HomeActivity;
import com.example.ma_ecommerce.buyer.ResetPasswordActivity;
import com.example.ma_ecommerce.model.Users;
import com.example.ma_ecommerce.prevalid.Prevalid;

import org.json.JSONArray;
import org.json.JSONObject;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    public String parentname = "Users";
    private Button log;
    private EditText phone, pass;
    private com.rey.material.widget.CheckBox remember;
    private ProgressDialog progressDialog;
    private TextView admin, notadmin, forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log = (Button) findViewById(R.id.button3);
        phone = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.pass);
        remember = (com.rey.material.widget.CheckBox) findViewById(R.id.cbox);
        admin = (TextView) findViewById(R.id.admin);
        notadmin = (TextView) findViewById(R.id.notadmin);
        forget = (TextView) findViewById(R.id.forget);
        progressDialog = new ProgressDialog(this);
        Paper.init(this);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("Login Admin");
                admin.setVisibility(View.INVISIBLE);
                notadmin.setVisibility(View.VISIBLE);
                parentname = "Admins";
            }
        });
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("Login");
                admin.setVisibility(View.VISIBLE);
                notadmin.setVisibility(View.INVISIBLE);
                parentname = "Users";

            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();

            }
        });
    }

    private void loginuser() {
        String phone1 = phone.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(phone1) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Login Account ");
            progressDialog.setMessage("Please Wait ");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            allow_access(phone1, password, parentname);

        }
    }


    public void allow_access(String phone, String password, String parentname) {
        if (remember.isChecked()) {
            Paper.book().write(Prevalid.userNumber, phone);
            Paper.book().write(Prevalid.pass, password);
            Paper.book().write(Prevalid.parentname, parentname);

        }


        String HTTP_SERVER_URL = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/login.php?phone=" + phone + "&password=" + password + "&parent=" + parentname;
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, HTTP_SERVER_URL, null, new Response.Listener<JSONArray>() {
                    String parentA = "";

                    @Override
                    public void onResponse(JSONArray response) {
                        Users users = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject row = response.getJSONObject(0);
                                int id = row.getInt("userID");
                                //Paper.book().write(Prevalid.id, id);
                                String name = row.getString("userName");
                                int userphone = row.getInt("userPhone");
                                String email = row.getString("userEmail");
                                String address = row.getString("userAddress");
                                String image = row.getString("userImage");
                                String answer1 = row.getString("userAnswer1");
                                String answer2 = row.getString("userAnswer2");
                                String userpass = row.getString("userPassword");
                                parentA = row.getString("parent");
                                if (userpass == null) {
                                    Toast.makeText(LoginActivity.this, "The password is incorrect", Toast.LENGTH_SHORT).show();
                                } else {
                                    users = new Users(name, userpass, address, image, email, answer1, answer2, userphone, id);
                                    Prevalid.online = users;
                                }

                            } catch (Exception ex) {
                                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                                Log.e("error", ex.toString());
                            }
                        }
                        progressDialog.dismiss();
                        if (parentA.equals("Admins") && Prevalid.online != null) {
                            Toast.makeText(LoginActivity.this, "Welcome Admin,login successful.. ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Paper.book().write(Prevalid.parentname, parentA);
                            startActivity(intent);
                        } else if (parentA.equals("Users") && Prevalid.online != null) {
                            Toast.makeText(LoginActivity.this, "Welcome, login successful.. ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Paper.book().write(Prevalid.parentname, parentA);

                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Password or Username!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("error", error.toString());
                        progressDialog.dismiss();
                    }


                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsArrRequest);
    }
}