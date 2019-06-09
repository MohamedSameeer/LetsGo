package com.example.letsgo.selectCategoryPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.example.letsgo.CategoryOfPlacePack.CategoryOfPlace;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SelecetCategoryPresenter {

    Context context;
    DatabaseReference cityRef;
    String city;
    ImageView cityImg;
    ProgressDialog progressDialog;
    SelecetCategoryPresenter(Context context, String city, ImageView cityImg, ProgressDialog progressDialog)
    {
        this.progressDialog=progressDialog;
        this.city=city;
        this.cityImg=cityImg;
        this.context=context;
        cityRef= FirebaseDatabase.getInstance().getReference().child("cities");
    }


    void getCityImage(){

        progressDialog.setMessage("getting data ..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        cityRef.child(city).child("Image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Picasso.get().load(dataSnapshot.getValue().toString()).into(cityImg);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
    void sendUserToSelectedCategory(String category,String city){

        Intent i = new Intent(context, CategoryOfPlace.class);

        i.putExtra("city",city);

        i.putExtra("category",category);
        Log.e(city+"seif bysb7 ",category+" seif bymsy");
        context.startActivity(i);

    }



}
