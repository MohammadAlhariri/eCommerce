package com.example.ma_ecommerce.buyer;

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
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.prevalid.Prevalid;

import java.util.HashMap;
import java.util.Map;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText shippmentName, shippmentPhone, shippmentAddress, shippmentCity;
    private Button confirmFinalOrder;
    private String totalAmount = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        confirmFinalOrder = findViewById(R.id.confirm_order_final);
        shippmentAddress = findViewById(R.id.shipment_address);
        shippmentCity = findViewById(R.id.shipment_city);
        shippmentName = findViewById(R.id.shipment_name);
        shippmentPhone = findViewById(R.id.shipment_phone);
        totalAmount = getIntent().getStringExtra("total");
        progressDialog = new ProgressDialog(this);

        Toast.makeText(this, "Total Amount is :$" + totalAmount, Toast.LENGTH_SHORT).show();
        confirmFinalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(shippmentCity.getText().toString()) ||
                        TextUtils.isEmpty(shippmentAddress.getText().toString()) ||
                        TextUtils.isEmpty(shippmentName.getText().toString()) ||
                        TextUtils.isEmpty(shippmentPhone.getText().toString())) {
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please fill information that needed", Toast.LENGTH_SHORT).show();

                } else {

                    confirmationOrder();
                }
            }
        });

    }

    private void confirmationOrder() {

        progressDialog.setTitle("Please waite ... ");
        progressDialog.setMessage("Please Wait ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/updateOrderState.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ConfirmFinalOrderActivity.this, response, Toast.LENGTH_SHORT).show();

                Log.e("db error", response);
                Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConfirmFinalOrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("uid", Prevalid.online.getID() + "");
                map.put("totalAmount", totalAmount);
                map.put("name", shippmentName.getText().toString());
                map.put("phone", shippmentPhone.getText().toString());
                map.put("city", shippmentCity.getText().toString());
                map.put("address", shippmentAddress.getText().toString());
                return map;
            }
        };

        queue.add(request);


    }

}