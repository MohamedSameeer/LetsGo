package com.example.letsgo.Events;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.letsgo.HomeFragment.HomePresenter;
import com.example.letsgo.HomeFragment.PlaceModel;
import com.example.letsgo.HomeFragment.PlacesAdapter;
import com.example.letsgo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsPresenter {

   private RecyclerView recyclerView;
   private LinearLayoutManager linearLayoutManager;
   private PlacesAdapter placesAdapter;
   private DatabaseReference eventRef;
   private ArrayList<PlaceModel>listOfPlaces;
    public EventsPresenter(View view){

        recyclerView=view.findViewById(R.id.container_events_places);
        linearLayoutManager=new LinearLayoutManager(view.getContext());
        placesAdapter=new PlacesAdapter();
        eventRef= FirebaseDatabase.getInstance().getReference().child("events");
        listOfPlaces=new ArrayList<>();
        Log.e("eveee","Constractorrr");
    }

    public PlacesAdapter getPlacesAdapter() {
        return placesAdapter;
    }

    public void getEvents(){
        Log.e("eveee","getEvents");
        listOfPlaces.clear();
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot item:dataSnapshot.getChildren()) {
                        listOfPlaces.add(new PlaceModel(
                                (item.child("Image").getValue())
                                ,(item.child("PlaceName").getValue())
                                ,(item.child("PlaceDescription").getValue())
                                /*,(item.child("DurationFrom").getValue())*/
                                ,(item.child("DurationTo").getValue())
                                ,(item.child("Category").getValue())
                                ,(item.child("City").getValue())
                                ,(item.child("Address").getValue())
                                ,(item.child("Price").getValue())
                                )
                        );
                    }
                    placesAdapter.setList(listOfPlaces);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(placesAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
