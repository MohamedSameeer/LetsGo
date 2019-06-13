package com.example.letsgo.Splash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.letsgo.LogIn.LogInActivity;
import com.example.letsgo.R;
import com.example.letsgo.Registeration.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Splash extends AppCompatActivity {

    private static final int RC_SIGN_IN =0 ;
    private Button logInBtnSplash,registerBtnSplash,signInGoogle;
    private static Context context;
    SplashPresenter splashPresenter;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initalization();
        context=getBaseContext();
        splashPresenter=new SplashPresenter(getContext());

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogleAccount();
            }
        });

        logInBtnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splashPresenter.goToLogIn();
            }
        });

        registerBtnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                splashPresenter.goToRegister();
            }
        });
    }

    private static Context getContext() {
        return context;
    }

    private void initalization(){
        progressDialog=new ProgressDialog(this);
        logInBtnSplash=findViewById(R.id.logInBtnSplash);
        registerBtnSplash=findViewById(R.id.registerBtnSplash);
        signInGoogle=findViewById(R.id.signInGoogleSplash);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    private void signInWithGoogleAccount(){
        mGoogleSignInClient = GoogleSignIn.getClient(Splash.this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        splashPresenter.loginWithGoogleAccount(progressDialog,requestCode,RC_SIGN_IN,data);
    }
}
