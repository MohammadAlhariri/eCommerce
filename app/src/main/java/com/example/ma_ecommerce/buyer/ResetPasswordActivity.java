package com.example.ma_ecommerce.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ma_ecommerce.R;

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
           // displayPreviousAnswers();

            pageNumber.setText("Set Question");
            titleQuestion.setText("Please set answer for the following security Questions");
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //setAnswers();
                    //displayPreviousAnswers();
                }
            });

        } else if (check.equals("login")) {
            findPhoneNumber.setVisibility(View.VISIBLE);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //verifyUser();
                }
            });


        }
    }
}