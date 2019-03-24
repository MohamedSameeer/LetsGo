package com.example.letsgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.letsgo.Adminstrator.PushingData;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    String adminId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        initialization();

        Button signOut=findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

    }

    //
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

        if (user!=null){
            String userId=user.getUid();
            if(userId.equals(adminId)){
                Intent i=new Intent(MainActivity.this, PushingData.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }else{
            enterToSplash();
        }
    }
    public void initialization(){
        mAuth=FirebaseAuth.getInstance();
        adminId="owQrAb02Z7WJ2u0ER6uPnqoNZum2";
    }
    private void enterToSplash(){
        Intent i=new Intent(MainActivity.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
