package com.example.letsgo.Events;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letsgo.HomeFragment.CategoriesAdapter;
import com.example.letsgo.HomeFragment.PlacesAdapter;
import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }

    View view;
    PlacesAdapter placesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_events, container, false);

       EventsPresenter eventsPresenter=new EventsPresenter(view);
       eventsPresenter.getEvents();
      placesAdapter=eventsPresenter.getPlacesAdapter();
      placesAdapter.setOnItemClickListener(new PlacesAdapter.OnItemClickListener() {
          @Override
          public void onClick(int position) {
              Intent i=new Intent(view.getContext(), PlaceActivity.class);
              i.putExtra("img",""+ placesAdapter.getList().get(position).getImg());
              i.putExtra("name",""+ placesAdapter.getList().get(position).getName());
              i.putExtra("city",""+ placesAdapter.getList().get(position).getCity());
              i.putExtra("category",""+ placesAdapter.getList().get(position).getCategory());
              i.putExtra("desc",""+ placesAdapter.getList().get(position).getDescription());
              i.putExtra("address",""+ placesAdapter.getList().get(position).getAddress());

              i.putExtra("price",""+ placesAdapter.getList().get(position).getPrice());
              i.putExtra("from",""+ placesAdapter.getList().get(position).getDurationFrom());
              i.putExtra("to",""+ placesAdapter.getList().get(position).getDurationTo());
              i.putExtra("fromClass","event");
              startActivity(i);
          }
      });
       return view;
    }

}
