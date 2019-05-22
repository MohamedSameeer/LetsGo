package com.example.letsgo.Favorite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.R;

public class Favorite extends AppCompatActivity {

    FavoriteAdapter placesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView recyclerView=findViewById(R.id.container_favorite_places);
      LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(this);

        final PlaceActivity placeActivity=new PlaceActivity();
        FavoritePresenter favoritePresenter=new FavoritePresenter(recyclerView,linearLayoutManager);
        placesAdapter =favoritePresenter.getPlacesAdapter();
        favoritePresenter.getData();
        placesAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i=new Intent(Favorite.this, PlaceActivity.class);
                i.putExtra("img",""+ placesAdapter.getList().get(position).getImg());
                i.putExtra("name",""+ placesAdapter.getList().get(position).getName());
                i.putExtra("desc",""+ placesAdapter.getList().get(position).getDescription());
                i.putExtra("address",""+ placesAdapter.getList().get(position).getAddress());
                i.putExtra("city",""+ placesAdapter.getList().get(position).getCity());
                i.putExtra("category",""+ placesAdapter.getList().get(position).getCategory());
                i.putExtra("price",""+ placesAdapter.getList().get(position).getPrice());
                i.putExtra("from",""+ placesAdapter.getList().get(position).getDurationFrom());
                i.putExtra("to",""+ placesAdapter.getList().get(position).getDurationTo());
                i.putExtra("fromClass","favorite");/*
                placeActivity.placeDetails(""+placesAdapter.getList().get(position).getName(),
                        ""+  placesAdapter.getList().get(position).getDescription(),
                        placesAdapter.getList().get(position).getImg()+"",
                        ""+ placesAdapter.getList().get(position).getPrice()
                        ,""+ placesAdapter.getList().get(position).getAddress()
                        ,""+ placesAdapter.getList().get(position).getDurationFrom()
                        ,""+ placesAdapter.getList().get(position).getDurationTo()
                        ,""+ placesAdapter.getList().get(position).getCity()
                        ,""+ placesAdapter.getList().get(position).getCategory()
                        ,"favorite"
                );*/
                startActivity(i);
            }
        });
    }
}
