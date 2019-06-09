package com.example.letsgo.Registeration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.letsgo.Country.CountryActivity;

import com.example.letsgo.DrawerMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class RegestrationPresenter {


    private String userName;
    private Context context;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    RegestrationPresenter(Context context, ProgressBar progressBar){

        this.context=context;
        this.progressBar=progressBar;
        mAuth=FirebaseAuth.getInstance();
        userRef=FirebaseDatabase.getInstance().getReference().child("User");
    }

     void verifyEmailAndPassword(EditText email, EditText password,EditText userName){

        String sEmail,sPassword,sUserName;
        sEmail=email.getText().toString().trim();
        sPassword=password.getText().toString().trim();
        sUserName=userName.getText().toString().trim();
        this.userName=sUserName;
        boolean flag=true;
        if(sEmail.isEmpty()){
            email.setError("can't leave this field empty");
            flag=false;
        }
        if(sPassword.isEmpty()){
            password.setError("can't be this field empty");
            flag=false;
        }
        if(sUserName.isEmpty()){
            userName.setError("can't be this field empty");
            flag=false;
        }
        if(flag){
            //TODO loading par
            createNewUser(sEmail,sPassword);
        }

    }
    private void createNewUser(final String email, String password){
       progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.e("RegestrationPres","successful Create");
                            String user=mAuth.getCurrentUser().getUid();
                            userRef.child(user).child("email").setValue(email);
                            userRef.child(user).child("id").setValue(user);
                            userRef.child(user).child("userName").setValue(userName);
                            progressBar.setVisibility(View.GONE);
                            enterToHome();
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Log.e("RegestrationPres","failed Create");
                        }
                    }
                });
    }
    private void enterToHome(){
        Intent i=new Intent(context, DrawerMainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);

    }
}
