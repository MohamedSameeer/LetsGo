package com.example.letsgo.categoryOfPlacePack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.letsgo.R;

public class categoryOfPlace extends AppCompatActivity {
    String categoyFromHomeFragment;
    String cityFromHomeAdapter;
    categoryOfPlacePresenter presenter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_places);
        intializeFields();



    }

    private void intializeFields() {
        Intent i = getIntent();
        categoyFromHomeFragment=i.getStringExtra("category");
        cityFromHomeAdapter=i.getStringExtra("city");
        recyclerView = findViewById(R.id.recyclerViewOfHistoricalPlaces);
        presenter=new categoryOfPlacePresenter(new ProgressDialog(this),categoyFromHomeFragment,cityFromHomeAdapter,this);
        presenter.getDataFromFirebase(recyclerView);
    }
}
