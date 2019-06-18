package com.example.letsgo.Payment;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class PaymentPresenter {

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference bookRef;
    String uid;
    PaymentPresenter(Boolean isTrip, Boolean isEvent){

        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        if(isTrip){
            bookRef=firebaseDatabase.getReference().child("Trip");
        }
        else if(isEvent){
            bookRef=firebaseDatabase.getReference().child("Event");
        }
        else{
            Log.e("PaymentPresenter","Error isTrip & isEvent false");
        }

    }
    void pushToFireBase(String city, String category, String place){
        bookRef.child(uid).child(place).child("PlaceCategory").setValue(category);
        bookRef.child(uid).child(place).child("PlaceCity").setValue(city);
        bookRef.child(uid).child(place).child("PlaceName").setValue(place);
    }
     boolean verifyCreditCard(String creditCardNumber){
        if(creditCardNumber.length()>=13 && creditCardNumber.length()<=19){
            return luhnCheck(creditCardNumber);
        }
        return false;
    }

     boolean luhnCheck(String card) {
        if (card == null)
            return false;
        char checkDigit = card.charAt(card.length() - 1);
        String digit = calculateCheckDigit(card.substring(0, card.length() - 1));
        return checkDigit == digit.charAt(0);
    }
     String calculateCheckDigit(String card) {
        if (card == null)
            return null;
        String digit;
        int[] digits = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            digits[i] = Character.getNumericValue(card.charAt(i));
        }
        for (int i = digits.length - 1; i >= 0; i -= 2)	{
            digits[i] += digits[i];
            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
        }
        sum = sum * 9;
        digit = sum + "";
        return digit.substring(digit.length() - 1);
    }
}
