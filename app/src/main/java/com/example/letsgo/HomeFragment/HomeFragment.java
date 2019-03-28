package com.example.letsgo.HomeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.letsgo.Adminstrator.CitiesName;
import com.example.letsgo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    View view;
    CategoriesAdapter adapter;
    Spinner homeCities;
    TextView textNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        final HomePresenter homePresenter=new HomePresenter(view);

        initialization();
        adapter=homePresenter.fillData();

        adapter.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String city=homeCities.getSelectedItem().toString();
                String category=adapter.list.get(position).getName();
                homePresenter.getPlaces(city,category,textNoData);
            }
        });

        return view;
    }


    private void initialization(){
        textNoData=view.findViewById(R.id.noData);
        homeCities=view.findViewById(R.id.home_cities);
        homeCities.setAdapter(new ArrayAdapter<String>(view.getContext()
                ,R.layout.support_simple_spinner_dropdown_item, CitiesName.cityName));
    }
}
