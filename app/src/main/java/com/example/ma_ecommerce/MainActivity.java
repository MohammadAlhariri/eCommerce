package com.example.ma_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.ma_ecommerce.buyer.HomeActivity;
import com.example.ma_ecommerce.model.Users;
import com.example.ma_ecommerce.prevalid.Prevalid;
//------------------
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.seller.SellerHomeActivity;
import com.example.ma_ecommerce.seller.SellerRegestrationActivity;

//-----------------
import org.json.JSONArray;
import org.json.JSONObject;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button user_login, user_join;
    private ProgressDialog progressDialog;
    private TextView seller;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //------------------------
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://mclweb2020.000webhostapp.com/mob/getProducts.php";
//        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0;i < response.length();i++) {
//                    try {
//                        JSONObject row = response.getJSONObject(i);
//                        int id = row.getInt("pid");
//                        String name = row.getString("name");
//                        int quantity = row.getInt("quantity");
//                        double price = row.getDouble("price");
//                        String category = row.getString("category");
//                        //products.add(new Product(id, name, quantity, price, category));
//                    }
//                    catch (Exception ex) {
//                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //adapter.notifyDataSetChanged();
//            }
//        }, null);
//
//        queue.add(request);
        //-------------------------

        user_login = (Button) findViewById(R.id.user_admin_login);
        user_join = (Button) findViewById(R.id.user_join);
        seller = findViewById(R.id.be_seller);


        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SellerRegestrationActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(this);

        Paper.init(this);
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

        if (phone != "" && pass != "") {
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass)) {
                allow_access(phone, pass,parent);
                progressDialog.setTitle("Already Login");
                progressDialog.setMessage("Please Wait ....... ");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        }


    }


    public void allow_access(String phone, String password,String parent) {


        String HTTP_SERVER_URL = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/login.php?phone=" + phone + "&password=" + password+"&parent="+parent ;
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (Request.Method.GET, HTTP_SERVER_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //mTxtDisplay.setText("Response: " + response.toString());
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, parent, Toast.LENGTH_SHORT).show();

                        if (parent.equals("Admins")) {
                            Toast.makeText(MainActivity.this, "Welcome Admin,login successful ", Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                            //Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                            //Prevalid.online = users;
                            //startActivity(intent);
                        } else if (parent.equals("Users")) {
                            Toast.makeText(MainActivity.this, "Welcome ,login successful ", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            // Prevalid.online = users;
                            startActivity(intent);
                        } else if (parent.equals("Sellers")) {
                            Toast.makeText(MainActivity.this, "Welcome ,login successful ", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            try {
//
//                                JSONObject row = response.getJSONObject(0);
//                                int id = row.getInt("userID");
//                                String name = row.getString("userName");
//                                int userphone = row.getInt("userPhone");
//                                String email = row.getString("userEmail");
//                                String address = row.getString("userAddress");
//                                String image = row.getString("userImage");
//                                String answer1 = row.getString("userAnswer1");
//                                String answer2 = row.getString("userAnswer2");
//                                String userpass = row.getString("userPassword");
//
//                                Prevalid.online = new Users(name, userpass, address, image, email, userphone, id);
                            } catch (Exception ex) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(MainActivity.this, SellerHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            // Prevalid.online = users;
                            startActivity(intent);
                        } else {
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
