package com.example.letsgo.Splash;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.letsgo.Country.CountryActivity;
import com.example.letsgo.DrawerMainActivity;
import com.example.letsgo.LogIn.LogInActivity;

import com.example.letsgo.Registeration.RegisterActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SplashPresenter {

    static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    static Context context;

    SplashPresenter(Context context){
        this.context=context;

    }

    public void loginWithGoogleAccount(int requestCode,int RC_SIGN_IN,Intent data){
        if (requestCode == RC_SIGN_IN && data != null) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {

                    firebaseAuthWithGoogle(account);
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("Splash", "Google sign in failed", e);
                // ...
            }
        }
    }
    private static void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("Splash Presenter", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("Splash Presenter", "signInWithCredential:success");
                            enterToHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Splash Presenter", "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }

    public  void goToLogIn(){
        Intent intent=new Intent(context, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public  void goToRegister(){
        Intent intent=new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void enterToHome(){
        Intent i=new Intent(context, DrawerMainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);

    }
}
