package com.example.ma_ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistertionActivity extends AppCompatActivity {
    private Button button;
    private EditText name, number, pass;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertion);
        button = (Button) findViewById(R.id.reg);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.phone2);
        pass = (EditText) findViewById(R.id.pass2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String phone = number.getText().toString();
        String names = name.getText().toString();
        String password = pass.getText().toString();
        progressDialog = new ProgressDialog(this);
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(names) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Create Account ");
            progressDialog.setMessage("Please Wait ");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validate(names, phone, password);
        }
    }

    private void validate(String names, String phone, String password) {
        final RequestQueue queue = Volley.newRequestQueue(RegistertionActivity.this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegistertionActivity.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.i("Reg", response);
                Intent intent = new Intent(RegistertionActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistertionActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> usermap = new HashMap<>();
                usermap.put("phone", phone);
                usermap.put("name", names);
                usermap.put("password", password);
                return usermap;

            }
        };
        queue.add(request);

    }

}