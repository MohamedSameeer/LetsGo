package com.example.letsgo.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.letsgo.R;

public class CategoryActivity extends AppCompatActivity {

    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent i=getIntent();
        city=i.getStringExtra("city");


    }
}
