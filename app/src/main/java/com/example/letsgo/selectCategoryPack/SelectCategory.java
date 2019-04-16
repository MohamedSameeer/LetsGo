package com.example.letsgo.selectCategoryPack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.letsgo.R;

public class SelectCategory extends AppCompatActivity {

    private ImageView eventImgCategory,historicalImgCategory,resturantImgCategory,entertainmentImgCategory
            ,hotelsImgCategory,natureImgCategory;


    private SelecetCategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);
        initlizeFields();

        eventImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nth yet
            }
        });


        historicalImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Historical");
            }
        });

        entertainmentImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Entertainment");
            }
        });

        hotelsImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Hotels & Resorts");
            }
        });

        natureImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Nature");
            }
        });

        resturantImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Cafe & Restaurant");
            }
        });




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
        presenter=new SelecetCategoryPresenter(this);
    }
}
