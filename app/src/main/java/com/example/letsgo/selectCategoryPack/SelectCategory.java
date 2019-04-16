package com.example.letsgo.selectCategoryPack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.letsgo.R;

public class SelectCategory extends AppCompatActivity {

    private ImageView eventImgCategory,historicalImgCategory,resturantImgCategory,entertainmentImgCategory
            ,hotelsImgCategory,natureImgCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);

        initlizeFields();
    }

    //connect id java with xml
    private void initlizeFields()
    {
        eventImgCategory=findViewById(R.id.eventImgCategory);
        historicalImgCategory=findViewById(R.id.historicalImgCategory);
        resturantImgCategory=findViewById(R.id.resturantImgCategory);
        entertainmentImgCategory=findViewById(R.id.cinemaImgCategory);
        hotelsImgCategory=findViewById(R.id.hotelsImgCategory);
        natureImgCategory=findViewById(R.id.natureImgCategory);

    }
}
