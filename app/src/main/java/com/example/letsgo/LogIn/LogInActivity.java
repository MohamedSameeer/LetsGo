package com.example.letsgo.LogIn;

import android.app.ProgressDialog;
import android.content.Context;
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
    private static Context context;
    private ProgressBar progressBarLogIn;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);
        context=getBaseContext();
        initalization();
        final LoginPresenter loginPresenter=new LoginPresenter(getContext(),progressBarLogIn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.verifyEmailAndPassword(username,password);
            }
        });
    }

    private static Context getContext() {
        return context;
    }

    private void initalization(){
        //fxlogo=findViewById(R.id.logInImage);
        username=findViewById(R.id.editTextUserName);
        password=findViewById(R.id.editTextPassword);
        logInBtn=findViewById(R.id.logInBtn);
        progressBarLogIn=findViewById(R.id.progresslogIn);
        //progressDialog=new ProgressDialog(getContext());
    }

    private void goToRegister(){
        Intent intent=new Intent(LogInActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
