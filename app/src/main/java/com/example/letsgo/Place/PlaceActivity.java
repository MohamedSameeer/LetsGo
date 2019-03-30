package com.example.letsgo.Place;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.MainActivity;
import com.example.letsgo.R;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity {

    TextView place_name,place_description;
    ImageView place_img;
    ImageButton addToFavorite;
    Toolbar myToolbar;
    FirebaseAuth mAuth;
    String placeName,placeDescription,placeImage,placeTo,placeFrom,placeCity,placeCategory,placePrice,placeAddress,fromClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        initialization();
        final PlacePresenter placePresenter=new PlacePresenter();
        setSupportActionBar(myToolbar);
        getData();
        place_name.setText(placeName);
        place_description.setText(placeDescription);
        Picasso.get().load(placeImage).into(place_img);
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placePresenter.addToFavorite(placeName,placeCity,placeCategory,fromClass);
            }
        });

    }

    private void initialization(){
        place_name=findViewById(R.id.main_place_name);
        place_description=findViewById(R.id.main_place_description);
        place_img=findViewById(R.id.main_place_img);
        myToolbar=findViewById(R.id.place_toolbar);
        addToFavorite=findViewById(R.id.addToFavorite);
        mAuth=FirebaseAuth.getInstance();
    }
    private void getData(){
        Intent i=getIntent();

        placeName=i.getStringExtra("name");
        placeDescription=i.getStringExtra("desc");
        placeImage=i.getStringExtra("img");
        placeAddress= i.getStringExtra("address");
        placeCity= i.getStringExtra("city");
        placeCategory= i.getStringExtra("category");
        placePrice= i.getStringExtra("price");
        placeFrom= i.getStringExtra("from");
        placeTo= i.getStringExtra("to");
       fromClass=i.getStringExtra("fromClass");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_option_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.contact_us:
                sendUserToContactUsActivity();
                break;
            case R.id.signOut:
                signOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void signOut() {
        mAuth.signOut();
        enterToSplash();
    }

    private void sendUserToContactUsActivity() {

        Intent i = new Intent (this, ContactUsActivity.class);
        startActivity(i);

    }
    private void enterToSplash(){
        Intent i=new Intent(PlaceActivity.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
