package com.example.letsgo.Registeration;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.letsgo.R;

public class RegisterActivity extends AppCompatActivity {
    private ImageView logo;
    private EditText regUserName,regEmail,regPassword;
    private Button regBtn;
    private static Context context;

    private ProgressBar progressBarRegister;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context=getBaseContext();
        initalization();

        final RegestrationPresenter regestrationPresenter=new RegestrationPresenter(getContext());

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                regestrationPresenter.verifyEmailAndPassword(regEmail,regPassword,regUserName);
            }
        });
    }

    private static Context getContext() {
        return context;
    }

    private void initalization(){
       // logo=findViewById(R.id.registerImage);
        regUserName=findViewById(R.id.registerUserName);
        regEmail=findViewById(R.id.registerEmail);
        regPassword=findViewById(R.id.registerPassword);
        regBtn=findViewById(R.id.registerBtn);
        progressBarRegister=findViewById(R.id.progresslogIn);
    }
}
