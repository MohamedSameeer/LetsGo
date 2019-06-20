package com.example.letsgo.CategoryOfPlacePack;

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
import android.widget.TextView;

import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.Container;
import com.example.letsgo.DrawerMainActivity;
import com.example.letsgo.Favorite.Favorite;

import com.example.letsgo.Place.PlaceActivity;
import com.example.letsgo.R;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CategoryOfPlace extends AppCompatActivity {
    String categoyFromHomeFragment;
    String cityFromHomeAdapter;
    TextView places;
    FirebaseAuth mAuth;
    Toolbar toolbar;
    List<PlaceModel> lst;
    CategoryOfPlacePresenter presenter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_places);
        intializeFields();
        final CategoryOfPlaceAdapter adapter;
        places.setText(categoyFromHomeFragment);
        setSupportActionBar(toolbar);
        adapter=presenter.getAdapter();
        adapter.setOnHeartClickListener(new CategoryOfPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

                lst=presenter.getAdapter().getListOfPlaces();
                if(!lst.get(position).isChecked()) {
                    presenter.addToFavorite(lst.get(position).getName().toString(), lst.get(position).getCity().toString()
                            , lst.get(position).getCategory().toString());
                    presenter.likePlace(lst.get(position).getName().toString(), lst.get(position).getCity().toString()
                            , lst.get(position).getCategory().toString());
                }else {
                    presenter.removeFromFavorite(lst.get(position).getName().toString());
                    presenter.removeLike(lst.get(position).getName().toString(), lst.get(position).getCity().toString()
                            , lst.get(position).getCategory().toString());
                }
            }
        });

        adapter.setOnItemClickListener(new CategoryOfPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i=new Intent(CategoryOfPlace.this, PlaceActivity.class);
                i.putExtra("img",""+ adapter.getListOfPlaces().get(position).getImg());
                i.putExtra("name",""+ adapter.getListOfPlaces().get(position).getName());
                i.putExtra("desc",""+ adapter.getListOfPlaces().get(position).getDescription());
                i.putExtra("address",""+ adapter.getListOfPlaces().get(position).getAddress());
                i.putExtra("city",""+ adapter.getListOfPlaces().get(position).getCity());
                i.putExtra("category",""+adapter.getListOfPlaces().get(position).getCategory());
                i.putExtra("price",""+adapter.getListOfPlaces().get(position).getPrice());
                i.putExtra("from",""+ adapter.getListOfPlaces().get(position).getDurationFrom());
                i.putExtra("to",""+ adapter.getListOfPlaces().get(position).getDurationTo());
                if(adapter.getListOfPlaces().get(position).getCategory().equals("Event")){
                    i.putExtra("isEvent",true);
                    i.putExtra("isTrip",false);
                }
                else if(adapter.getListOfPlaces().get(position).getCategory().equals("Trip")){
                    i.putExtra("isEvent",false);
                    i.putExtra("isTrip",true);
                }else{
                    i.putExtra("isEvent",false);
                    i.putExtra("isTrip",false);
                }
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
        presenter=new CategoryOfPlacePresenter(new ProgressDialog(this),categoyFromHomeFragment,cityFromHomeAdapter,this,recyclerView);
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
        Intent i=new Intent(CategoryOfPlace.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

}
