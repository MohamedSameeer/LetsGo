package com.example.letsgo.Favorite;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    private ProgressDialog progressDialog;
    FavoritePresenter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, Context context){

        listOfPlaces=new ArrayList<>();
        this.recyclerView=recyclerView;
        this.linearLayoutManager=linearLayoutManager;
        placesAdapter=new FavoriteAdapter(listOfPlaces,context);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null)
            userId=mAuth.getCurrentUser().getUid();
        eventRef=FirebaseDatabase.getInstance().getReference().child("events");
        citiesRef= FirebaseDatabase.getInstance().getReference().child("cities");
        favRef=FirebaseDatabase.getInstance().getReference().child("favorite");
        progressDialog=new ProgressDialog(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placesAdapter);

    }

    FavoriteAdapter getPlacesAdapter() {
        return placesAdapter;
    }

    void getData(){
        progressDialog.setMessage("getting data ..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        favRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    listOfPlaces.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        //Log.e("FavoritePresenter",data.child("from").getValue()+"");
                       // String from=data.child("from").getValue()+"";
                      //  String isAdded=data.child("isAdded").getValue()+"";
                        citiesRef.child(data.child("PlaceCity").getValue()+"")
                                .child(data.child("PlaceCategory").getValue()+"")
                                .child(data.child("PlaceName").getValue()+"")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
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
                    }

                }
                placesAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
    void likePlace(String placeName, String placeCity, String placeCategory){
        citiesRef.child(placeCity).child(placeCategory).child(placeName).child(userId).setValue(userId);
    }
    void addToFavorite(String placeName, String placeCity, String placeCategory) {
        favRef.child(userId).child(placeName).child("PlaceName").setValue(placeName);
        favRef.child(userId).child(placeName).child("PlaceCity").setValue(placeCity);
        favRef.child(userId).child(placeName).child("PlaceCategory").setValue(placeCategory);
        //  favoriteRef.child(userId).child(placeName).child("from").setValue(fromClass);


    }
    void removeFromFavorite(String placeName) {
        favRef.child(userId).child(placeName).removeValue();
    }

    void removeLike(String placeName, String placeCity, String placeCategory) {
        citiesRef.child(placeCity).child(placeCategory).child(placeName).child(userId).removeValue();
    }
}
