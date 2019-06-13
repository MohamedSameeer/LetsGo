package com.example.letsgo.Event;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.Program.ProgramOfPlaceAdapter;
import com.example.letsgo.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    View view;
    public EventFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    EventPresenter presenter;
    EventOfPlaceAdapter adapter;
    List<PlaceModel>lst;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_event, container, false);
        Initialization();

        adapter=presenter.getAdapter();
        adapter.setOnHeartClickListener(new EventOfPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                lst=presenter.getAdapter().getListOfPlaces();


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
        adapter.setOnItemClickListener(new EventOfPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i=new Intent(view.getContext(), PlaceActivity.class);
                i.putExtra("img",""+ adapter.getListOfPlaces().get(position).getImg());
                i.putExtra("name",""+ adapter.getListOfPlaces().get(position).getName());
                i.putExtra("desc",""+ adapter.getListOfPlaces().get(position).getDescription());
                i.putExtra("address",""+ adapter.getListOfPlaces().get(position).getAddress());
                i.putExtra("city",""+ adapter.getListOfPlaces().get(position).getCity());
                i.putExtra("category",""+adapter.getListOfPlaces().get(position).getCategory());
                i.putExtra("price",""+adapter.getListOfPlaces().get(position).getPrice());
                i.putExtra("from",""+ adapter.getListOfPlaces().get(position).getDurationFrom());
                i.putExtra("to",""+ adapter.getListOfPlaces().get(position).getDurationTo());
                i.putExtra("isBook",true);
                //  i.putExtra("fromClass","home");

                startActivity(i);
            }
        });
        return view;

    }

    private void Initialization() {
        recyclerView=view.findViewById(R.id.event_container);
        presenter=new EventPresenter(recyclerView,new ProgressDialog(view.getContext()),view.getContext());

    }

}
