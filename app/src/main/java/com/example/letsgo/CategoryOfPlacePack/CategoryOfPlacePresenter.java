package com.example.letsgo.CategoryOfPlacePack;

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

public class    CategoryOfPlacePresenter {
    PlaceModel model;
    ProgressDialog progressDialog;
    String category;
    String city;
    ArrayList<PlaceModel> places;
    CategoryOfPlaceAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference categogryRef;
    RecyclerView recyclerView;
    Context context;
    String userId;

    DatabaseReference favoriteRef,placeRef;
    CategoryOfPlacePresenter(ProgressDialog progressDialog, String category, String city, Context context, RecyclerView recyclerView){
        this.city=city;
        this.category=category;
        this.progressDialog=progressDialog;
        Log.e("Category",category+" "+city);
        favoriteRef= FirebaseDatabase.getInstance().getReference().child("favorite");
        placeRef=FirebaseDatabase.getInstance().getReference().child("cities");
        mAuth=FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();
        places=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        Log.e("fianl ",city+category);
        categogryRef=firebaseDatabase.getReference().child("cities").child(city).child(category);
        this.context=context;
        adapter=new CategoryOfPlaceAdapter(places,context);
        this.recyclerView=recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

    }

    CategoryOfPlaceAdapter getAdapter(){
        return adapter;
    }

    void getDataFromFirebase(){
    progressDialog.setMessage("getting data ..");
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(true);
    progressDialog.show();
    categogryRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            places.clear();
            for (DataSnapshot d:dataSnapshot.getChildren()) {
                int i=1;
                i++;
                Log.e("on change ","iiiiii:   "+i);
                Log.e(category,city);
                model = new PlaceModel();
               // model.setAddress(d.child("Address").getValue().toString());
                if(d.hasChild("Lat")&& d.hasChild("Lng")){
                    model.setLat(d.child("Lat").getValue());
                    model.setLng(d.child("Lng").getValue());
                }else{
                    model.setLat("26.8206");
                    model.setLng("30.8025");
                    Log.e("CategoryOfP","sssss");
                }
                model.setCategory(d.child("Category").getValue().toString());
                model.setCity(d.child("City").getValue().toString());
                model.setDurationFrom(d.child("DurationFrom").getValue().toString());
                model.setDurationTo(d.child("DurationTo").getValue().toString());
                model.setImg(d.child("Image").getValue().toString());
                model.setDescription(d.child("PlaceDescription").getValue().toString());
                model.setName(d.child("PlaceName").getValue().toString());
                model.setPrice(d.child("Price").getValue().toString());
              //  Log.e("yyyyyyyyyyyyyyyyyyyyy"+model.getAddress().toString(),model.getCategory().toString());
                places.add(model);
                adapter.notifyDataSetChanged();

            }
                progressDialog.dismiss();
        }




        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("yyyyyyyyyyyyyyyyy","cancelled");
            progressDialog.dismiss();

        }
    });

    }

    void likePlace(String placeName, String placeCity, String placeCategory){
        placeRef.child(placeCity).child(placeCategory).child(placeName).child(userId).setValue(userId);
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
        placeRef.child(placeCity).child(placeCategory).child(placeName).child(userId).removeValue();
    }
}
