package com.example.letsgo.Country;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.letsgo.Adminstrator.CitiesName;
import com.example.letsgo.Adminstrator.PushingData;
import com.example.letsgo.Category.CategoryActivity;
import com.example.letsgo.MainActivity;
import com.example.letsgo.R;
import com.example.letsgo.Splash.Splash;
import com.example.letsgo.selectCategoryPack.SelectCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.isapanah.awesomespinner.AwesomeSpinner;

public class CountryActivity extends AppCompatActivity {

    String city="";
    Button go;
    AwesomeSpinner my_spinner;
    String adminId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        initialization();
        Log.e("main","country");

        my_spinner.setAdapter(new ArrayAdapter<String>(this
                , R.layout.support_simple_spinner_dropdown_item, CitiesName.cityName));

        my_spinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {

                city = my_spinner.getSelectedItem();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city.isEmpty()){
                    Toast.makeText(CountryActivity.this,"you should choose a gov",Toast.LENGTH_LONG).show();
                }
                else{
                Intent i = new Intent(getApplicationContext(), SelectCategory.class);
                i.putExtra("city", city);
                Log.e("city", city);
                startActivity(i);
                }
            }
        });
    }


    void initialization() {
        my_spinner = findViewById(R.id.my_spinner);
        go = findViewById(R.id.logInBtn);
        mAuth = FirebaseAuth.getInstance();
        adminId = "owQrAb02Z7WJ2u0ER6uPnqoNZum2";
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            if (userId.equals(adminId)) {
                Intent i = new Intent(CountryActivity.this, PushingData.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        } else {
            enterToSplash();
        }

    }

    private void enterToSplash() {
        Intent i = new Intent(CountryActivity.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

}
