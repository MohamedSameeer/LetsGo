package com.example.letsgo.LogIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.letsgo.Country.CountryActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {

    FirebaseAuth mAuth;
    private ProgressBar progressBar;
    Context context;
    public LoginPresenter(Context context, ProgressBar progressBar){

        this.context=context;
        this.progressBar=progressBar;
        mAuth=FirebaseAuth.getInstance();
    }

    public void verifyEmailAndPassword(EditText email, EditText password){

        String sEmail,sPassword;
        sEmail=email.getText().toString().trim();
        sPassword=password.getText().toString().trim();
        boolean flag=true;
        if(sEmail.isEmpty()){
            email.setError("can't leave this field empty");
            flag=false;
        }
        if(sPassword.isEmpty()){
            password.setError("can't be this field empty");
            flag=false;
        }
        if(flag){
            //TODO loading par
            loginWithEmailAndPassword(sEmail,sPassword);
        }

    }
    private void loginWithEmailAndPassword(String email,String password){
      progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e("LoginPresenter","Login Successful");
                            progressBar.setVisibility(View.GONE);
                            enterToHome();
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("LoginPresenter",task.getException().getMessage());
                        }
                    }
                });
    }

    private void enterToHome(){
        Intent i=new Intent(context, CountryActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);

    }
}
