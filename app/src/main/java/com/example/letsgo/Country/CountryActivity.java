package com.example.letsgo.Country;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.letsgo.Adminstrator.CitiesName;
import com.example.letsgo.Category.CategoryActivity;
import com.example.letsgo.R;
import com.isapanah.awesomespinner.AwesomeSpinner;

public class CountryActivity extends AppCompatActivity {

    String city;
    Button go;
    AwesomeSpinner my_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        initialization();

        my_spinner.setAdapter(new ArrayAdapter<String>(this
                ,R.layout.support_simple_spinner_dropdown_item, CitiesName.cityName));

        my_spinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                city=my_spinner.getSelectedItem();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), CategoryActivity.class);
                i.putExtra("city",city);
                Log.e("city",city);
                startActivity(i);
            }
        });
    }
    void initialization(){
        my_spinner =findViewById(R.id.my_spinner);
        go=findViewById(R.id.logInBtn);
    }
}
