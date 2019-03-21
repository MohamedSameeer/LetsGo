package com.example.letsgo.LogIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.letsgo.R;
import com.example.letsgo.Registeration.RegisterActivity;

public class LogInActivity extends AppCompatActivity {
    private ImageView logo;
    private EditText username,password;
    private Button logInBtn;
    private TextView registerTextView;
    private ProgressBar progressBarLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);
        initalization();
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
    }

    private void initalization(){
        logo=findViewById(R.id.logInImage);
        username=findViewById(R.id.editTextUserName);
        password=findViewById(R.id.editTextPassword);
        logInBtn=findViewById(R.id.logInBtn);
        registerTextView=findViewById(R.id.registerTextView);
        progressBarLogIn=findViewById(R.id.progresslogIn);
    }

    private void goToRegister(){
        Intent intent=new Intent(LogInActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
