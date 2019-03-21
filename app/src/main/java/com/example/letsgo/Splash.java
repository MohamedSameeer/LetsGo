package com.example.letsgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.letsgo.LogIn.LogInActivity;
import com.example.letsgo.Registeration.RegisterActivity;

public class Splash extends AppCompatActivity {
    private Button logInBtnSplash,registerBtnSplash,signInGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initalization();
        logInBtnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogIn();
            }
        });
        registerBtnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
    }

    private void initalization(){
        logInBtnSplash=findViewById(R.id.logInBtnSplash);
        registerBtnSplash=findViewById(R.id.registerBtnSplash);
        signInGoogle=findViewById(R.id.signInGoogleSplash);

    }
    private void goToLogIn(){

        Intent intent=new Intent(Splash.this, LogInActivity.class);
        startActivity(intent);

    }
    private void goToRegister(){
        Intent intent=new Intent(Splash.this, RegisterActivity.class);
        startActivity(intent);
    }
}
