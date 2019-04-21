package com.example.letsgo.FavoriteFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.letsgo.HomeFragment.PlaceModel;
import com.example.letsgo.HomeFragment.PlacesAdapter;
import com.example.letsgo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class FavoritePresenter {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FavoriteAdapter placesAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference citiesRef,favRef,eventRef;
    private String userId;
    private ArrayList<PlaceModel> listOfPlaces;
    FavoritePresenter(RecyclerView recyclerView,LinearLayoutManager linearLayoutManager){

        this.recyclerView=recyclerView;
       this.linearLayoutManager=linearLayoutManager;
        placesAdapter=new FavoriteAdapter();
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null)
            userId=mAuth.getCurrentUser().getUid();
        eventRef=FirebaseDatabase.getInstance().getReference().child("events");
        citiesRef= FirebaseDatabase.getInstance().getReference().child("cities");
        favRef=FirebaseDatabase.getInstance().getReference().child("favorite").child(userId);
        listOfPlaces=new ArrayList<>();
        placesAdapter.setList(listOfPlaces);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);

    }

    public FavoriteAdapter getPlacesAdapter() {
        return placesAdapter;
    }

    void getData(){
        listOfPlaces.clear();
        favRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        //Log.e("FavoritePresenter",data.child("from").getValue()+"");
                       // String from=data.child("from").getValue()+"";
                        String isAdded=data.child("isAdded").getValue()+"";
                        if(/*from.equals("home") &&*/ isAdded.equals("true")){
                            citiesRef.child(data.child("PlaceCity").getValue()+"")
                                    .child(data.child("PlaceCategory").getValue()+"")
                                    .child(data.child("PlaceName").getValue()+"")
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Log.e("FavoritePresenter",dataSnapshot.child("PlaceName").getValue()+"");
                                            listOfPlaces.add(new PlaceModel(
                                                            (dataSnapshot.child("Image").getValue())
                                                            ,(dataSnapshot.child("PlaceName").getValue())
                                                            ,(dataSnapshot.child("PlaceDescription").getValue())
                                                           // ,(dataSnapshot.child("DurationFrom").getValue())
                                                            ,(dataSnapshot.child("DurationTo").getValue())
                                                            ,(dataSnapshot.child("Category").getValue())
                                                            ,(dataSnapshot.child("City").getValue())
                                                            ,(dataSnapshot.child("Address").getValue())
                                                            ,(dataSnapshot.child("Price").getValue())

                                                    )
                                            );
                                            placesAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }/*else if (from.equals("event") && isAdded.equals("true")){
                                eventRef.child(data.child("PlaceName").getValue().toString())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Log.e("FavoritePresenter",dataSnapshot.child("PlaceName").getValue()+"");
                                                listOfPlaces.add(new PlaceModel(
                                                                (dataSnapshot.child("Image").getValue())
                                                                ,(dataSnapshot.child("PlaceName").getValue())
                                                                ,(dataSnapshot.child("PlaceDescription").getValue())
                                                                ,(dataSnapshot.child("DurationFrom").getValue())
                                                                ,(dataSnapshot.child("DurationTo").getValue())
                                                                ,(dataSnapshot.child("Category").getValue())
                                                                ,(dataSnapshot.child("City").getValue())
                                                                ,(dataSnapshot.child("Address").getValue())
                                                                ,(dataSnapshot.child("Price").getValue())

                                                        )
                                                );
                                                placesAdapter.notifyDataSetChanged();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                        }*/

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
