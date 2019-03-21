package com.example.letsgo.Adminstrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.letsgo.R;

public class PushingData extends AppCompatActivity {
    private Spinner spinnerCity,spinnerCateogry;
    private EditText placeName,description,price,address,durationFrom,durationTo;
    private Button uploadImages,pushData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushing_data);
        initialization();
        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        pushData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initialization(){
        spinnerCity=findViewById(R.id.spinnerCities);
        spinnerCity.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cityName));

        spinnerCateogry=findViewById(R.id.spinnerCategories);
        spinnerCateogry.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cateogryName));

        placeName=findViewById(R.id.adminPlaceName);
        description=findViewById(R.id.adminDescName);
        price=findViewById(R.id.adminPrice);
        address=findViewById(R.id.adminAddress);
        durationFrom=findViewById(R.id.adminDurationFrom);
        durationTo=findViewById(R.id.adminDurationTo);
        uploadImages=findViewById(R.id.uploadImagesBtn);
        pushData=findViewById(R.id.pushDataBtn);
    }

}
