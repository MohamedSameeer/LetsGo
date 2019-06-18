package com.example.letsgo.Adminstrator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.letsgo.R;
import com.google.firebase.auth.FirebaseAuth;

public class PushingData extends AppCompatActivity {
    private Spinner spinnerCity,spinnerCateogry;
    private EditText placeName,description,price,durationFrom,durationTo,lat,lng;
    private Button uploadImages,pushData,logOut;
    private ProgressDialog progressDialog;
    String sPlaceName,sPlaceDescription,sPrice,latitude,longitude,sDurationFrom,sDurationTo,sCity,sCategory;
    Uri imageUri;
    FirebaseAuth mAuth;
    private PushingDataPresenter pushingDataPresenter;
    private static Context context;
    private static final int PICK_IMG_REQUEST =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushing_data);
        context=getBaseContext();
        mAuth=FirebaseAuth.getInstance();
        initialization();
        pushData.setClickable(false);
        pushingDataPresenter=new PushingDataPresenter(getContext());
        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  progressBar.setVisibility(View.VISIBLE);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMG_REQUEST);


            }
        });

        pushData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pushAllData()){
                    Log.e("ps1","done her");
                  //  progressBar.setVisibility(View.VISIBLE);
                    //pushingDataPresenter.uploadPicture(imageUri,progressBar);
                        Log.e("ps1","done her2");
                       if(pushingDataPresenter.pushing(sPlaceName, sPlaceDescription, sPrice, longitude,latitude, sDurationFrom, sDurationTo, sCity, sCategory)){
                            placeName.setText("");
                            description.setText("");
                            price.setText("");
                            lat.setText("");
                            lng.setText("");
                            durationFrom.setText("");
                            durationTo.setText("");
                       }

                }
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushingDataPresenter.logOut();
                finish();
            }
        });
    }

    private static Context getContext() {
        return context;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data != null){
             imageUri = data.getData();
             pushingDataPresenter.uploadPicture(imageUri,progressDialog);
             pushData.setClickable(true);
        }
        else {
            Toast.makeText(this, "Please Pick Photo", Toast.LENGTH_LONG).show();
            pushData.setClickable(false);
        }
    }
    private boolean saveDataNotNull(){
        boolean flag=true;
        if (sCategory.isEmpty()) {
            flag=false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        if (sCity.isEmpty()){
            flag=false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();}

        if (sPlaceName.isEmpty()) {
            flag=false;
            placeName.setError("This Field can't be empty");
        }
        if (sPlaceDescription.isEmpty()){
            flag=false;
            description.setError("This Field can't be empty");
        }
        if (sPrice.isEmpty()) {
            flag=false;
            price.setError("This Field can't be empty");
        }
        if (latitude.isEmpty()){
            flag=false;
            lat.setError("This Field can't be empty");
        }
        if(longitude.isEmpty()){
            flag=false;
            lng.setError("This Field can't be empty");
        }
        if (sDurationFrom.isEmpty()) {
            flag=false;
            durationFrom.setError("This Field can't be empty");
        }
        if (sDurationTo.isEmpty()) {
            flag=false;
            durationTo.setError("This Field can't be empty");
        }
        return flag;
    }

    private boolean pushAllData() {
        sCategory=spinnerCateogry.getSelectedItem().toString();
        sCity=spinnerCity.getSelectedItem().toString();
        sPlaceName=placeName.getText().toString().trim();
        sPlaceDescription=description.getText().toString().trim();
        sPrice=price.getText().toString().trim();
        latitude=lat.getText().toString().trim();
        longitude=lng.getText().toString().trim();
        sDurationFrom=durationFrom.getText().toString().trim();
        sDurationTo=durationTo.getText().toString().trim();
        return saveDataNotNull();
    }

    private void initialization(){
        spinnerCity=findViewById(R.id.spinnerCities);
        spinnerCity.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cityName));
       // progressBar=findViewById(R.id.progressPush);
        progressDialog=new ProgressDialog(this);
        spinnerCateogry=findViewById(R.id.spinnerCategories);
        spinnerCateogry.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cateogryName));

        logOut=findViewById(R.id.adminLogout);
        placeName=findViewById(R.id.adminPlaceName);
        description=findViewById(R.id.adminDescName);
        price=findViewById(R.id.adminPrice);
        lat=findViewById(R.id.lat);
        lng=findViewById(R.id.lng);
        durationFrom=findViewById(R.id.adminDurationFrom);
        durationTo=findViewById(R.id.adminDurationTo);
        uploadImages=findViewById(R.id.uploadImagesBtn);
        pushData=findViewById(R.id.pushDataBtn);
    }

}
