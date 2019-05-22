package com.example.letsgo.Reviews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewPresenter implements IPresenter {

    FirebaseAuth mAuth;
    String currentUser;
    FirebaseDatabase database;
    DatabaseReference reference,userRef;
    String userId;
    String userName;
    public ReviewPresenter(){
        intializeFields();
    }

    @Override
    public void intializeFields() {
    mAuth = FirebaseAuth.getInstance();
    currentUser=mAuth.getCurrentUser().getUid();
    database = FirebaseDatabase.getInstance();
    reference=database.getReference().child("cities");
    userRef=database.getReference().child("User");
    userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
    getUserName();
    }
    private void getUserName(){

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName=dataSnapshot.child(userId).child("userName").getValue()+"";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getReviewAndupLoadReViewToFirebase(final Context context, String review,String city,String category ,String name) {
        DatabaseReference reviewRef= reference.child(city).child(category).child(name).child("Reviews").push();
        reviewRef.child("user").setValue(userName);
        reviewRef.child("review").setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){ Toast.makeText(context, "your review has been submitted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "your review has not been submitted try again after check your connection with internet", Toast.LENGTH_LONG).show();

            }
        });

    }



}
