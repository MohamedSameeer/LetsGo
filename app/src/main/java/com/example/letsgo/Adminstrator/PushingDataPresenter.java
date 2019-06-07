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


import com.example.letsgo.Splash.Splash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
   // ProgressBar progressBar;
    String url,city,category,placeName;
    Map<Object , Object>objectMap=new HashMap<>();
    PushingDataPresenter(Context context){
        cities= FirebaseDatabase.getInstance().getReference().child("cities");

        this.context=context;
        storageReference = FirebaseStorage.getInstance().getReference().child("places_images");
        mAuth=FirebaseAuth.getInstance();
    }

    void uploadPicture(Uri iamgeUri, final ProgressDialog progressDialog){
       // final ProgressDialog progressDialog = progressBar;
        progressDialog.setTitle("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String imageName = UUID.randomUUID().toString() + ".jpg";
       // this.progressBar=progressBar;
        storageReference.child(imageName).putFile(iamgeUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       // progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Upload photo complete", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        storageReference.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url=uri.toString();
                              /*  if(!url.isEmpty()){
                                    objectMap.put("Image",url);
                                    if(isChecked) {
                                        events.child(placeName).setValue(objectMap);
                                    }
                                   else
                                       cities.child(city).child(category).child(placeName).setValue(objectMap);

                                    Toast.makeText(context, "Update data Complete", Toast.LENGTH_SHORT).show();

                                }*/
                            }
                        });
                    }
                })   .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });;
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
    public boolean pushing(String sPlaceName, String sPlaceDescription, String sPrice, String sAddress, String sDurationFrom
            , String sDurationTo,String sCity,String sCategory,boolean isEvent) {
        Map<Object ,Object >s=new HashMap<>();
        if(url!=null) {
            isChecked = isEvent;
            s.put("PlaceName", sPlaceName);
            s.put("PlaceDescription", sPlaceDescription);
            s.put("Price", sPrice);
            s.put("Address", sAddress);
            s.put("DurationFrom", sDurationFrom);
            s.put("DurationTo", sDurationTo);
            s.put("City", sCity);
            s.put("Image", url);
            objectMap.putAll(s);
            city = sCity;
            placeName = sPlaceName;
            category = sCategory;
            if (isChecked) {
                s.put("Category", "Event");
                cities.child(city).child("Event").child(placeName).setValue(s);
            } else {
                s.put("Category", sCategory);
                cities.child(city).child(category).child(placeName).setValue(s);
            }
            // progressBar.setVisibility(View.GONE);
            Toast.makeText(context, "Update data Complete", Toast.LENGTH_SHORT).show();
            return true;
            //cities.child(city).child(category).child(placeName).setValue(objectMap);
        }else
            Toast.makeText(context, "Please update photo", Toast.LENGTH_LONG).show();
        return false;


    }
}
