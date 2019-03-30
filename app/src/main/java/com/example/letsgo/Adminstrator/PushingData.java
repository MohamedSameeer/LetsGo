package com.example.letsgo.Adminstrator;

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

import com.example.letsgo.MainActivity;
import com.example.letsgo.R;
import com.google.firebase.auth.FirebaseAuth;

public class PushingData extends AppCompatActivity {
    private Spinner spinnerCity,spinnerCateogry;
    private EditText placeName,description,price,address,durationFrom,durationTo;
    private Button uploadImages,pushData,logOut;
    private ProgressBar progressBar;
    String sPlaceName,sPlaceDescription,sPrice,sAddress,sDurationFrom,sDurationTo,sCity,sCategory;
    Uri imageUri;
    CheckBox isEvent;
    FirebaseAuth mAuth;
    private static Context context;
    private static final int PICK_IMG_REQUEST =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushing_data);
        context=getBaseContext();
        mAuth=FirebaseAuth.getInstance();
        initialization();
        final PushingDataPresenter pushingDataPresenter=new PushingDataPresenter(getContext());
        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    progressBar.setVisibility(View.VISIBLE);
                    pushingDataPresenter.uploadPicture(imageUri,progressBar);
                        Log.e("ps1","done her2");
                        pushingDataPresenter.pushing(sPlaceName, sPlaceDescription, sPrice, sAddress, sDurationFrom, sDurationTo, sCity, sCategory, isEvent.isChecked());
                        placeName.setText("");
                        description.setText("");
                        price.setText("");
                        address.setText("");
                        durationFrom.setText("");
                        durationTo.setText("");
                        Toast.makeText(PushingData.this, "Update data Complete", Toast.LENGTH_SHORT).show();

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
        if(resultCode==RESULT_OK && data != null)
             imageUri = data.getData();
        else
            Toast.makeText(this, "Please Pick Photo", Toast.LENGTH_LONG).show();

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
            flag = false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        if (sPlaceDescription.isEmpty()){
            flag=false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        if (sPrice.isEmpty()) {
            flag = false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        if (sAddress.isEmpty()){
            flag=false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        if (sDurationFrom.isEmpty()) {
            flag = false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        if (sDurationTo.isEmpty()) {
            flag = false;
            Toast.makeText(this, "missing field", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    private boolean pushAllData() {
        sCategory=spinnerCateogry.getSelectedItem().toString();
        sCity=spinnerCity.getSelectedItem().toString();
        sPlaceName=placeName.getText().toString().trim();
        sPlaceDescription=description.getText().toString().trim();
        sPrice=price.getText().toString().trim();
        sAddress=address.getText().toString().trim();
        sDurationFrom=durationFrom.getText().toString().trim();
        sDurationTo=durationTo.getText().toString().trim();
        return saveDataNotNull();
    }

    private void initialization(){
        isEvent=findViewById(R.id.isEvent);
        spinnerCity=findViewById(R.id.spinnerCities);
        spinnerCity.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cityName));
        progressBar=findViewById(R.id.progressPush);
        spinnerCateogry=findViewById(R.id.spinnerCategories);
        spinnerCateogry.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cateogryName));

        logOut=findViewById(R.id.adminLogout);
        placeName=findViewById(R.id.adminPlaceName);
        description=findViewById(R.id.adminDescName);
        price=findViewById(R.id.adminPrice);
        address=findViewById(R.id.adminAddress);
        durationFrom=findViewById(R.id.adminDurationFrom);
        durationTo=findViewById(R.id.adminDurationTo);
        uploadImages=findViewById(R.id.uploadImagesBtn);
        pushData=findViewById(R.id.pushDataBtn);
    }

}
