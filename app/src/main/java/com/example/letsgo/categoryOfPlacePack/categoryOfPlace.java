package com.example.letsgo.categoryOfPlacePack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.FavoriteFragment.Favorite;
import com.example.letsgo.HomeFragment.PlaceModel;
import com.example.letsgo.MainActivity;
import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.R;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class categoryOfPlace extends AppCompatActivity {
    String categoyFromHomeFragment;
    String cityFromHomeAdapter;
    TextView places;
    FirebaseAuth mAuth;
    Toolbar toolbar;
    List<PlaceModel> lst;
    categoryOfPlacePresenter presenter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_places);
        intializeFields();
        final CatoegryOfPlaceAdapter adapter;
        places.setText(categoyFromHomeFragment);
        setSupportActionBar(toolbar);
        adapter=presenter.getAdapter();
        adapter.setOnHeartClickListener(new CatoegryOfPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                lst=presenter.getAdapter().getListOfPlaces();
                presenter.addToFavorite(lst.get(position).getName().toString(),lst.get(position).getCity().toString()
                        ,lst.get(position).getCategory().toString());

            }
        });

        adapter.setOnItemClickListener(new CatoegryOfPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i=new Intent(categoryOfPlace.this, PlaceActivity.class);
                i.putExtra("img",""+ adapter.getListOfPlaces().get(position).getImg());
                i.putExtra("name",""+ adapter.getListOfPlaces().get(position).getName());
                i.putExtra("desc",""+ adapter.getListOfPlaces().get(position).getDescription());
                i.putExtra("address",""+ adapter.getListOfPlaces().get(position).getAddress());
                i.putExtra("city",""+ adapter.getListOfPlaces().get(position).getCity());
                i.putExtra("category",""+adapter.getListOfPlaces().get(position).getCategory());
                i.putExtra("price",""+adapter.getListOfPlaces().get(position).getPrice());
                i.putExtra("from",""+ adapter.getListOfPlaces().get(position).getDurationFrom());
                i.putExtra("to",""+ adapter.getListOfPlaces().get(position).getDurationTo());
              //  i.putExtra("fromClass","home");

                startActivity(i);
            }
        });


    }

    private void intializeFields() {
        mAuth=FirebaseAuth.getInstance();
        Intent i = getIntent();
        categoyFromHomeFragment=i.getStringExtra("category");
        cityFromHomeAdapter=i.getStringExtra("city");
        Log.e(categoyFromHomeFragment+"seeeeeeif",cityFromHomeAdapter+"amor");
        places=findViewById(R.id.places_name);
        recyclerView = findViewById(R.id.recyclerViewOfHistoricalPlaces);
        presenter=new categoryOfPlacePresenter(new ProgressDialog(this),categoyFromHomeFragment,cityFromHomeAdapter,this,recyclerView);
        presenter.getDataFromFirebase();
        toolbar=findViewById(R.id.places_tool_bar);
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
                sendUserToFavoriteActivity();
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

    private void sendUserToFavoriteActivity() {
        Intent i = new Intent (this, Favorite.class);
        startActivity(i);
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
        Intent i=new Intent(categoryOfPlace.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

}
