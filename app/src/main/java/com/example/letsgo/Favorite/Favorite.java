package com.example.letsgo.Favorite;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.R;

import java.util.List;

public class Favorite extends Fragment {

    View view;
    FavoriteAdapter placesAdapter;
    FavoritePresenter presenter;
    List<PlaceModel> lst;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_favorite, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.container_favorite_places);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        //final PlaceActivity placeActivity = new PlaceActivity();
        presenter = new FavoritePresenter(recyclerView, linearLayoutManager,view.getContext());
        placesAdapter = presenter.getPlacesAdapter();
        presenter.getData();
        placesAdapter.setOnHeartClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                lst=presenter.getPlacesAdapter().getListOfPlaces();
                if(!lst.get(position).isChecked()) {
                    presenter.addToFavorite(lst.get(position).getName().toString(), lst.get(position).getCity().toString()
                            , lst.get(position).getCategory().toString());
                    presenter.likePlace(lst.get(position).getName().toString(), lst.get(position).getCity().toString()
                            , lst.get(position).getCategory().toString());
                }else {
                    presenter.removeFromFavorite(lst.get(position).getName().toString());
                    presenter.removeLike(lst.get(position).getName().toString(), lst.get(position).getCity().toString()
                            , lst.get(position).getCategory().toString());
                }
            }
        });
        placesAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(view.getContext(), PlaceActivity.class);
                i.putExtra("img", "" + placesAdapter.getListOfPlaces().get(position).getImg());
                i.putExtra("name", "" + placesAdapter.getListOfPlaces().get(position).getName());
                i.putExtra("desc", "" + placesAdapter.getListOfPlaces().get(position).getDescription());
                i.putExtra("address", "" + placesAdapter.getListOfPlaces().get(position).getAddress());
                i.putExtra("city", "" + placesAdapter.getListOfPlaces().get(position).getCity());
                i.putExtra("category", "" + placesAdapter.getListOfPlaces().get(position).getCategory());
                i.putExtra("price", "" + placesAdapter.getListOfPlaces().get(position).getPrice());
                i.putExtra("from", "" + placesAdapter.getListOfPlaces().get(position).getDurationFrom());
                i.putExtra("to", "" + placesAdapter.getListOfPlaces().get(position).getDurationTo());
                i.putExtra("fromClass", "favorite");

                startActivity(i);
            }
        });
        return view;
    }
}
