package com.example.letsgo.Event;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.letsgo.Program.ProgramOfPlaceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class EventPresenter {

    private DatabaseReference eventRef,cityRef,favoriteRef;
    private List<PlaceModel>listOfEvent;
    private RecyclerView recyclerView;
    private EventOfPlaceAdapter eventAdapter;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private String userId;
    EventPresenter(RecyclerView recyclerView, ProgressDialog progressDialog, Context context){

        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();

        eventRef= FirebaseDatabase.getInstance().getReference().child("Event").child(userId);
        cityRef = FirebaseDatabase.getInstance().getReference().child("cities");
        favoriteRef= FirebaseDatabase.getInstance().getReference().child("favorite");

        listOfEvent=new ArrayList<>();
        this.recyclerView=recyclerView;
        this.progressDialog=progressDialog;
        eventAdapter=new EventOfPlaceAdapter(listOfEvent,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(eventAdapter);
        getData();

    }
    EventOfPlaceAdapter getAdapter() {
        return eventAdapter;
    }

    void getData(){
        progressDialog.setMessage("getting data ..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    listOfEvent.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()) {
                        Log.e("ProgramPresenter",data.child("PlaceName").getValue()+"");
                        Log.e("ProgramPresenter",data.child("PlaceCategory").getValue()+"");
                        Log.e("ProgramPresenter",data.child("PlaceCity").getValue()+"");
                        cityRef.child(data.child("PlaceCity").getValue()+"")
                                .child(data.child("PlaceCategory").getValue()+"")
                                .child(data.child("PlaceName").getValue()+"")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        listOfEvent.add(new PlaceModel(
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
                                        eventAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                }
                eventAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
    void likePlace(String placeName, String placeCity, String placeCategory){
       cityRef.child(placeCity).child(placeCategory).child(placeName).child(userId).setValue(userId);
    }
    void addToFavorite(String placeName, String placeCity, String placeCategory) {
        favoriteRef.child(userId).child(placeName).child("PlaceName").setValue(placeName);
        favoriteRef.child(userId).child(placeName).child("PlaceCity").setValue(placeCity);
        favoriteRef.child(userId).child(placeName).child("PlaceCategory").setValue(placeCategory);
        //  favoriteRef.child(userId).child(placeName).child("from").setValue(fromClass);


    }
    void removeFromFavorite(String placeName) {
        favoriteRef.child(userId).child(placeName).removeValue();
    }

    void removeLike(String placeName, String placeCity, String placeCategory) {
       cityRef.child(placeCity).child(placeCategory).child(placeName).child(userId).removeValue();
    }
}
