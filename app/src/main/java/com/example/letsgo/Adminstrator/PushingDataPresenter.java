package com.example.letsgo.Adminstrator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.letsgo.MainActivity;
import com.example.letsgo.Splash.Splash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PushingDataPresenter  implements IpushingData{
    DatabaseReference cities ,events;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    boolean flag;
    Context context;
    boolean isChecked;
    String url,city,category,placeName;
    Map<Object , Object>objectMap=new HashMap<>();
    PushingDataPresenter(Context context){
        cities= FirebaseDatabase.getInstance().getReference().child("cities");
        events=FirebaseDatabase.getInstance().getReference().child("events");
        this.context=context;
        storageReference = FirebaseStorage.getInstance().getReference().child("places_images");
        mAuth=FirebaseAuth.getInstance();
    }

    void uploadPicture(Uri iamgeUri, final ProgressBar progressBar){
        final String imageName = UUID.randomUUID().toString() + ".jpg";
        storageReference.child(imageName).putFile(iamgeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url=uri.toString();
                        if(!url.isEmpty()){
                            objectMap.put("Image",url);
                            if(isChecked) {
                                events.child(placeName).setValue(objectMap);

                            }
                           else
                               cities.child(city).child(category).child(placeName).setValue(objectMap);
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });
    }
    void logOut(){
            mAuth.signOut();
            enterToHome();
    }
    private void enterToHome(){
        Intent i=new Intent(context, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }
    @Override
    public void pushing(String sPlaceName, String sPlaceDescription, String sPrice, String sAddress, String sDurationFrom
            , String sDurationTo,String sCity,String sCategory,boolean isEvent) {
        Map<Object ,Object >s=new HashMap<>();
        isChecked=isEvent;
        s.put("PlaceName",sPlaceName);
        s.put("PlaceDescription",sPlaceDescription);
        s.put("Price",sPrice);
        s.put("Address",sAddress);
        s.put("DurationFrom",sDurationFrom);
        s.put("DurationTo",sDurationTo);
        s.put("City",sCity);
        s.put("Category",sCategory);
        objectMap.putAll(s);
        city=sCity;
        placeName=sPlaceName;
        category=sCategory;
        Log.e("Well done", "Wellllllllllll done");
        //cities.child(city).child(category).child(placeName).setValue(objectMap);
    }
}
