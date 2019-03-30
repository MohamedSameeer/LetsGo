package com.example.letsgo.Place;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlacePresenter {
    private DatabaseReference favoriteRef;
    private String userId;
    private static boolean isAdded=true;
    PlacePresenter(){
        favoriteRef= FirebaseDatabase.getInstance().getReference().child("favorite");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
}
