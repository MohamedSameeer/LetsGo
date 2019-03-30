package com.example.letsgo.HomeFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.letsgo.Adminstrator.CitiesName;
import com.example.letsgo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePresenter {

    private DatabaseReference cityRef;
    private View view;
    private RecyclerView recyclerView,placeRecyclerView;
    private LinearLayoutManager linearLayoutManager,placeLayOutManager;
    private ArrayList<CategoryModel>listOfCategories;
    private ArrayList<PlaceModel>listOfPlaces;
    private CategoriesAdapter adapter;
    private PlacesAdapter placesAdapter;

     PlacesAdapter getPlacesAdapter() {
        return placesAdapter;
    }

     HomePresenter(View view){
        placesAdapter=new PlacesAdapter();
        cityRef= FirebaseDatabase.getInstance().getReference().child("cities");
        this.view=view;
        //Categories
        recyclerView=view.findViewById(R.id.category_container);
        linearLayoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        listOfCategories=new ArrayList<>();
        //Places
        placeRecyclerView=view.findViewById(R.id.container_places);
        placeLayOutManager=new LinearLayoutManager(view.getContext());
        listOfPlaces=new ArrayList<>();
    }

     CategoriesAdapter fillData(){


        listOfCategories.add(new CategoryModel(R.drawable.back,"Historical"));
        listOfCategories.add(new CategoryModel(R.drawable.back,"Hotels & Resorts"));
        listOfCategories.add(new CategoryModel(R.drawable.back,"Nature"));
        listOfCategories.add(new CategoryModel(R.drawable.back,"Entertainment"));
        listOfCategories.add(new CategoryModel(R.drawable.back,"Cafe & Restaurant"));

        adapter=new CategoriesAdapter(listOfCategories);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return adapter;
    }


    void getPlaces(String city, String category, final TextView noData){
        listOfPlaces.clear();
        cityRef.child(city).child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        for (DataSnapshot child:dataSnapshot.getChildren()){
                            Log.e("childdddddd",child.child("PlaceName").getValue().toString());
                            listOfPlaces.add(new PlaceModel(
                                    (child.child("Image").getValue())
                                    ,(child.child("PlaceName").getValue())
                                    ,(child.child("PlaceDescription").getValue())
                                    ,(child.child("DurationFrom").getValue())
                                    ,(child.child("DurationTo").getValue())
                                    ,(child.child("Category").getValue())
                                    ,(child.child("City").getValue())
                                    ,(child.child("Address").getValue())
                                    ,(child.child("Price").getValue())
                                    )
                            );
                            Log.e("HomePresenter",listOfPlaces.get(0).getName().toString()+"");
                        }
                        noData.setVisibility(View.GONE);
                        //placesAdapter=new PlacesAdapter(listOfPlaces);
                        placesAdapter.setList(listOfPlaces);
                        placeRecyclerView.setLayoutManager(placeLayOutManager);
                        placeRecyclerView.setAdapter(placesAdapter);

                    }else{
                        noData.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("HomePresenter","OnCancelled"+databaseError.getMessage());
            }
        });

    }
}
