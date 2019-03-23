package com.example.letsgo.Adminstrator;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PushingDataPresenter  implements IpushingData{
    DatabaseReference cities ;
    StorageReference storageReference;
    String url,city,category,placeName;
    Map<Object , Object>objectMap=new HashMap<>();
    PushingDataPresenter(){
        cities= FirebaseDatabase.getInstance().getReference().child("cities");
        storageReference = FirebaseStorage.getInstance().getReference().child("places_images");
    }

    void uploadPicture(Uri iamgeUri){

        final String imageName = UUID.randomUUID().toString() + ".jpg";

        storageReference.child(imageName).putFile(iamgeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url=uri.toString();
                        cities.child(city).child(category).child(placeName).setValue(objectMap);
                    }
                });
            }
        });
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
        s.put("Image",url);
        s.put("Category",sCategory);
        objectMap.putAll(s);
        city=sCity;
        placeName=sPlaceName;
        category=sCategory;
    }
}
