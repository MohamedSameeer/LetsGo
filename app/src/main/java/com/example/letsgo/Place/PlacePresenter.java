package com.example.letsgo.Place;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RatingBar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlacePresenter {
    private DatabaseReference favoriteRef;
    private String userId;
    private ReviewRecyclerView reviewRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private   FirebaseAuth mAuth;
    private static boolean isAdded=true;
    private DatabaseReference cityRef;
    ArrayList<ReviewModel> arrayList; ReviewModel current;
    PlacePresenter(RecyclerView recyclerView, Context context){
        favoriteRef= FirebaseDatabase.getInstance().getReference().child("favorite");
        cityRef=   FirebaseDatabase.getInstance().getReference().child("cities");
        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList();
        this.recyclerView=recyclerView;
        linearLayoutManager=new LinearLayoutManager(context);
        reviewRecyclerView =new ReviewRecyclerView(arrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewRecyclerView);
        //34an ata2ked howa b null wala la2 a7teaty
        if(mAuth.getCurrentUser()!=null)
            userId= mAuth.getCurrentUser().getUid();

    }

    public void addToFavorite(String placeName,String placeCity, String placeCategory, String fromClass){
        favoriteRef.child(userId).child(placeName).child("PlaceName").setValue(placeName);
        favoriteRef.child(userId).child(placeName).child("PlaceCity").setValue(placeCity);
        favoriteRef.child(userId).child(placeName).child("PlaceCategory").setValue(placeCategory);
        favoriteRef.child(userId).child(placeName).child("from").setValue(fromClass);
        favoriteRef.child(userId).child(placeName).child("isAdded").setValue(isAdded);
        if (isAdded){
            isAdded=false;
        }else
            isAdded=true;

    }

    void getReviews(String placeCity,String placeCategory, String placeName) {

        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference()
                .child("cities").child(placeCity).child(placeCategory).child(placeName).child("Reviews");

        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()) {

                    current=new ReviewModel();
                    if(d.hasChild("review")){
                    current.setReview(d.child("review").getValue().toString());}
                    if (d.hasChild("user") ) {
                        current.setUserId(d.child("user").getValue().toString());
                    }
                    arrayList.add(current);


                }
                reviewRecyclerView.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

     void sendRate(float ratting,String placeCity,String placeCategory, String placeName) {
         cityRef.child(placeCity).child(placeCategory).child(placeName)
                    .child("Rating").child(userId).child("rate").setValue(ratting);

    }

     void getRate(final RatingBar ratingBar, String placeCity, String placeCategory, String placeName) {
        cityRef.child(placeCity).child(placeCategory).child(placeName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("PlacePresenter",dataSnapshot+"");
                        if(dataSnapshot.hasChild("Rating")&&dataSnapshot.child("Rating").hasChild(userId)){
                            {
                                String rating=dataSnapshot.child("Rating").child(userId).child("rate").getValue().toString();
                                float rate=Float.parseFloat(rating);
                                ratingBar.setRating(rate);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
