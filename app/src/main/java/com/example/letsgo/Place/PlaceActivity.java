package com.example.letsgo.Place;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.Container;
import com.example.letsgo.Favorite.Favorite;
import com.example.letsgo.Payment.PaymentActivity;
import com.example.letsgo.R;
import com.example.letsgo.Reviews.ReviewActivity;
import com.example.letsgo.Splash.Splash;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView place_name,place_description,place_duration_to,place_duration_from,place_price;
    ImageView place_img;
    Button book;
    ImageButton addToFavorite;
    Toolbar myToolbar;
    FirebaseAuth mAuth;
    RecyclerView reviewRecycler;
    double lat,lng;

    Button showMore;
    RatingBar ratingBar;
    String placeName,placeDescription,placeImage,placeTo,placeFrom,placeCity,placeCategory,placePrice,placeAddress,fromClass;
    boolean isBook;
    PlacePresenter placePresenter;
    FloatingActionButton floatingActionButton;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        initialization();
        placePresenter=new PlacePresenter(reviewRecycler,getApplicationContext());
        getData();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        if(isBook)
            book.setVisibility(View.VISIBLE);
        else
            book.setVisibility(View.GONE);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PlaceActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });
        placePresenter.getReviews(placeCity,placeCategory,placeName);
        setSupportActionBar(myToolbar);
        place_name.setText(placeName);
        place_description.setText(placeDescription);
        place_duration_to.setText(placeTo);
        place_duration_from.setText(placeFrom);
        place_price.setText(placePrice);
        //place_location.setText(placeAddress);

        Picasso.get().load(placeImage).into(place_img);
       /* addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placePresenter.addToFavorite(placeName,placeCity,placeCategory,fromClass);
            }
        });*/

       ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               placePresenter.sendRate(rating,placeCity,placeCategory,placeName);
               Toast.makeText(PlaceActivity.this, ""+rating, Toast.LENGTH_SHORT).show();
           }
       });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();

            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        float rate=ratingBar.getRating();
        Log.e("PlaceActivity",rate+"");
        savedInstanceState.putFloat("rate",rate);
        Toast.makeText(this, ""+rate, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, ""+savedInstanceState.getFloat("rate"), Toast.LENGTH_SHORT).show();
        ratingBar.setRating(savedInstanceState.getFloat("rate"));
    }

    private void sendIntent() {
        Intent i = new Intent(getApplicationContext(), ReviewActivity.class);
        i.putExtra("name",""+ placeName);
        i.putExtra("city",""+placeCity);
        i.putExtra("category",""+ placeCategory);
        startActivity(i);



    }

    private void initialization(){
        place_name=findViewById(R.id.main_place_name);
        place_description=findViewById(R.id.main_place_description);
        place_img=findViewById(R.id.main_place_img);
        place_duration_from=findViewById(R.id.duration_from);
        place_duration_to=findViewById(R.id.duration_to);
        place_price=findViewById(R.id.price);
       // place_location=findViewById(R.id.location);
        myToolbar=findViewById(R.id.place_toolbar);
        //addToFavorite=findViewById(R.id.addToFavorite);
        mAuth=FirebaseAuth.getInstance();
        reviewRecycler = findViewById(R.id.reviews_container);
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        book=findViewById(R.id.book);
        ratingBar=findViewById(R.id.rating_bar);
    }
    private void getData(){
        Intent i=getIntent();
        placeName=i.getStringExtra("name");
        placeCity= i.getStringExtra("city");
        placeCategory= i.getStringExtra("category");
        placeDescription=i.getStringExtra("desc");
        placeImage=i.getStringExtra("img");
        //placeAddress= i.getStringExtra("address");
        lat=i.getDoubleExtra("lat",26.8206);
        lng=i.getDoubleExtra("lng",30.8025);
        placePrice= i.getStringExtra("price");
        placeFrom= i.getStringExtra("from");
        placeTo= i.getStringExtra("to");
        fromClass=i.getStringExtra("fromClass");
        isBook=i.getBooleanExtra("isBook",false);
        placePresenter.getRate(ratingBar, placeCity,placeCategory,placeName);
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
            case R.id.favoritee:
                sendUserToFavoritectivity();
                break;
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

    private void sendUserToFavoritectivity() {
        Intent i = new Intent (this, Container.class);
        i.putExtra("flag",1);
        startActivity(i);
    }

    private void signOut() {
        mAuth.signOut();
        enterToSplash();
    }

    private void sendUserToContactUsActivity() {

        Intent i = new Intent (this, Container.class);
        i.putExtra("flag",2);
        startActivity(i);

    }
    private void enterToSplash(){
        Intent i=new Intent(PlaceActivity.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        float zoomLevel = 7.0f;
        LatLng sydney = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in "+placeName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoomLevel));
    }

}
