package com.example.letsgo.Adminstrator;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.letsgo.R;

public class PushingData extends AppCompatActivity {
    private Spinner spinnerCity,spinnerCateogry;
    private EditText placeName,description,price,address,durationFrom,durationTo;
    private Button uploadImages,pushData;
    String sPlaceName,sPlaceDescription,sPrice,sAddress,sDurationFrom,sDurationTo,sCity,sCategory;
    Uri imageUri;
    private static final int PICK_IMG_REQUEST =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushing_data);
        initialization();
        final PushingDataPresenter pushingDataPresenter=new PushingDataPresenter();
        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent=new Intent();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMG_REQUEST);


            }
        });

        pushData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushAllData();
                pushingDataPresenter.uploadPicture(imageUri);
                pushingDataPresenter.pushing(sPlaceName,sPlaceDescription,sPrice,sAddress,sDurationFrom,sDurationTo,sCity,sCategory);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();

    }

    private void pushAllData() {
        sCategory=spinnerCateogry.getSelectedItem().toString();
        sCity=spinnerCity.getSelectedItem().toString();
        sPlaceName=placeName.getText().toString().trim();
        sPlaceDescription=description.getText().toString().trim();
        sPrice=price.getText().toString().trim();
        sAddress=address.getText().toString().trim();
        sDurationFrom=durationFrom.getText().toString().trim();
        sDurationTo=durationTo.getText().toString().trim();
    }

    private void initialization(){
        spinnerCity=findViewById(R.id.spinnerCities);
        spinnerCity.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cityName));

        spinnerCateogry=findViewById(R.id.spinnerCategories);
        spinnerCateogry.setAdapter(new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,CitiesName.cateogryName));

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
