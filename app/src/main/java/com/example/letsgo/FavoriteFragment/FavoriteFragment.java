package com.example.letsgo.FavoriteFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }

    View view;
    FavoriteAdapter placesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_favorite, container, false);
        FavoritePresenter favoritePresenter=new FavoritePresenter(view);
        placesAdapter =favoritePresenter.getPlacesAdapter();
        favoritePresenter.getData();
        placesAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i=new Intent(view.getContext(), PlaceActivity.class);
                i.putExtra("img",""+ placesAdapter.getList().get(position).getImg());
                i.putExtra("name",""+ placesAdapter.getList().get(position).getName());
                i.putExtra("desc",""+ placesAdapter.getList().get(position).getDescription());
                i.putExtra("address",""+ placesAdapter.getList().get(position).getAddress());
                i.putExtra("city",""+ placesAdapter.getList().get(position).getCity());
                i.putExtra("category",""+ placesAdapter.getList().get(position).getCategory());
                i.putExtra("price",""+ placesAdapter.getList().get(position).getPrice());
                i.putExtra("from",""+ placesAdapter.getList().get(position).getDurationFrom());
                i.putExtra("to",""+ placesAdapter.getList().get(position).getDurationTo());
                i.putExtra("fromClass","favorite");
                startActivity(i);
            }
        });
        return view;
    }

}
