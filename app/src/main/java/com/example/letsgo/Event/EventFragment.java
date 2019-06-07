package com.example.letsgo.Event;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return view;

    }

    private void Initialization() {
        recyclerView=view.findViewById(R.id.event_container);
        presenter=new EventPresenter(recyclerView,new ProgressDialog(view.getContext()),view.getContext());

    }

}
