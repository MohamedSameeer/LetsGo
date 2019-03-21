package com.example.letsgo.Adminstrator;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PushingDataPresenter  implements IpushingData{
    DatabaseReference cities ;
    PushingDataPresenter(){
        cities= FirebaseDatabase.getInstance().getReference().child("cities");
    }
    @Override
    public void pushing(String sPlaceName, String sPlaceDescription, String sPrice, String sAddress, String sDurationFrom
            , String sDurationTo,String sCity,String sCategory) {
        Map<Object ,Object >s=new HashMap<>();
        s.put("PlaceName",sPlaceName);
        s.put("PlaceDescription",sPlaceDescription);
        s.put("Price",sPrice);
        s.put("Address",sAddress);
        s.put("DurationFrom",sDurationFrom);
        s.put("DurationTo",sDurationTo);
        s.put("City",sCity);
        s.put("Category",sCategory);
        cities.child(sCity).child(sCategory).child(sPlaceName).setValue(s);
    }
}
