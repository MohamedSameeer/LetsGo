package com.example.letsgo.categoryOfPlacePack;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.letsgo.HomeFragment.PlaceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class categoryOfPlacePresenter {
    PlaceModel model;
    ProgressDialog progressDialog;
    String category;
    String city;
    ArrayList<PlaceModel> places;
    CatoegryOfPlaceAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference categogryRef;
    Context context;

    categoryOfPlacePresenter(ProgressDialog progressDialog,String category,String city,Context context){
        this.city=city;
        this.category=category;
        this.progressDialog=progressDialog;
        places=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        Log.e("fianl ",city+category);
        categogryRef=firebaseDatabase.getReference().child("cities").child(city).child(category);
        this.context=context;

    }



    void getDataFromFirebase(RecyclerView recyclerView){
    progressDialog.setMessage("getting data ..");
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(true);
    progressDialog.show();
    categogryRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



            for (DataSnapshot d:dataSnapshot.getChildren()) {
                int i=1;
                i++;
                Log.e("on change ","iiiiii:   "+i);
                Log.e(category,city);
                model = new PlaceModel();
                model.setAddress(d.child("Address").getValue().toString());
                model.setCategory(d.child("Category").getValue().toString());
                model.setCity(d.child("City").getValue().toString());
                model.setDurationFrom(d.child("DurationFrom").getValue().toString());
                model.setDurationTo(d.child("DurationTo").getValue().toString());
                model.setImg(d.child("Image").getValue().toString());
                model.setDescription(d.child("PlaceDescription").getValue().toString());
                model.setName(d.child("PlaceName").getValue().toString());
                model.setPrice(d.child("Price").getValue().toString());
                Log.e("yyyyyyyyyyyyyyyyyyyyy"+model.getAddress().toString(),model.getCategory().toString());
                places.add(model);
                adapter.notifyDataSetChanged();

            }
           // Log.e("d5lna on Change","w kosom kda "+dataSnapshot.child("Address").getValue().toString());

            progressDialog.dismiss();

        }




        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("yyyyyyyyyyyyyyyyy","cancelled");
            progressDialog.dismiss();

        }
    });
        adapter=new CatoegryOfPlaceAdapter(places,context);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.setAdapter(adapter);


    }


}
