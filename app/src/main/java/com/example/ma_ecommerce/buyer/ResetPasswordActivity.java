package com.example.ma_ecommerce.buyer;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ma_ecommerce.R;
import com.example.ma_ecommerce.prevalid.Prevalid;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {
    private String check = "";
    private EditText findPhoneNumber, question1, question2;
    private Button verifyButton;
    private TextView pageNumber, titleQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);
        findPhoneNumber = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        verifyButton = findViewById(R.id.verify_btn);
        pageNumber = findViewById(R.id.page_number);
        titleQuestion = findViewById(R.id.title_questions);
        check = getIntent().getStringExtra("check");
    }

    @Override
    protected void onStart() {
        super.onStart();

        findPhoneNumber.setVisibility(View.GONE);

        if (check.equals("settings")) {
            displayPreviousAnswers();

            pageNumber.setText("Set Question");
            titleQuestion.setText("Please set answer for the following security Questions");
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswers();
                }
            });

        } else if (check.equals("login")) {
            findPhoneNumber.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });


        }
    }

    private void verifyUser() {

        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();
        String phone = findPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(findPhoneNumber.getText().toString()) || TextUtils.isEmpty(answer1) || TextUtils.isEmpty(answer2)) {
            Toast.makeText(ResetPasswordActivity.this, "Please fill all required input ", Toast.LENGTH_SHORT).show();

        } else {
//
            final RequestQueue queue = Volley.newRequestQueue(this);
//
            String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/checkAnswerGET.php?answer1=" + answer1 + "&answer2=" + answer2 + "&phone=" + phone;
//
            JsonArrayRequest jsArrRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject row = response.getJSONObject(0);
                                    String id = row.getString("userID");
                                    Toast.makeText(ResetPasswordActivity.this, "Here we go " + id, Toast.LENGTH_LONG).show();
                                    if (!TextUtils.isEmpty(id)) {
                                        Intent intent = new Intent(ResetPasswordActivity.this, ChangePasswordActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("uid", id);
                                        startActivity(intent);
                                    }
                                } catch (Exception ex) {
                                    Toast.makeText(ResetPasswordActivity.this, "error", Toast.LENGTH_SHORT).show();
                                    Log.e("error", ex.toString());
                                }
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.e("error", error.toString());
                        }


                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(jsArrRequest);

        }
    }

    private void setAnswers() {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();
        if (TextUtils.isEmpty(answer1) || TextUtils.isEmpty(answer2)) {
            Toast.makeText(ResetPasswordActivity.this, "Please answer both ", Toast.LENGTH_SHORT).show();
        } else {
            final RequestQueue queue = Volley.newRequestQueue(this);

            String url = "https://ecommerceliu.000webhostapp.com/eCommerceLIU/updateSecurityAnswer.php";


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ResetPasswordActivity.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResetPasswordActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String uid = Prevalid.online.getID() + "";
                    HashMap<String, String> usermap = new HashMap<>();
                    usermap.put("answer1", answer1);
                    usermap.put("answer2", answer2);
                    usermap.put("uid", uid);

                    return usermap;
                }
            };

            queue.add(request);
        }
    }

    private void displayPreviousAnswers() {
        question1.setText(Prevalid.online.getAnswer1());
        question2.setText(Prevalid.online.getAnswer1());
    }
}