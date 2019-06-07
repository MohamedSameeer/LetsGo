package com.example.letsgo.Program;


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
import java.util.List;

class ProgramPresenter {

    private DatabaseReference programRef,cityRef,favoriteRef;
    private List<PlaceModel>listOfProgram;
    private RecyclerView recyclerView;
    private  ProgramOfPlaceAdapter programAdapter;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private String userId;
    ProgramPresenter(RecyclerView recyclerView, ProgressDialog progressDialog, Context context){

        programRef= FirebaseDatabase.getInstance().getReference().child("Program");
        cityRef = FirebaseDatabase.getInstance().getReference().child("cities");
        favoriteRef= FirebaseDatabase.getInstance().getReference().child("favorite");
        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        listOfProgram=new ArrayList<>();
        this.recyclerView=recyclerView;
        this.progressDialog=progressDialog;
        programAdapter=new ProgramOfPlaceAdapter(listOfProgram,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(programAdapter);
        getData();

    }
    ProgramOfPlaceAdapter getAdapter() {
        return programAdapter;
    }

    void getData(){
        progressDialog.setMessage("getting data ..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        programRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    listOfProgram.clear();
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

                                        listOfProgram.add(new PlaceModel(
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
                                        programAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                }
                programAdapter.notifyDataSetChanged();
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
