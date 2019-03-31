package com.example.letsgo.Reviews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewPresenter implements IPresenter {

    FirebaseAuth mAuth;
    String currentUser;
    FirebaseDatabase database;
    DatabaseReference reference;

    public ReviewPresenter(){
        intializeFields();
    }

    @Override
    public void intializeFields() {
    mAuth = FirebaseAuth.getInstance();
    currentUser=mAuth.getCurrentUser().getUid();
    database = FirebaseDatabase.getInstance();
    reference=database.getReference().child("cities");

    }


    @Override
    public void getReviewAndupLoadReViewToFirebase(final Context context, String review,String city,String category ,String name) {
        DatabaseReference reviewRef= reference.child(city).child(category).child(name).child("Reviews").push();
        reviewRef.child("user").setValue(currentUser);
        reviewRef.child("review").setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){ Toast.makeText(context, "your review has been submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
