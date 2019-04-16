package com.example.letsgo.Category;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CategoryPresenter {
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    CategoryPresenter(String city){

        databaseReference= FirebaseDatabase.getInstance().getReference().child(city);
    }

    void getImg(){
        databaseReference.child("img").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //  Picasso.get().load(dataSnapshot.getValue().toString()).into();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
