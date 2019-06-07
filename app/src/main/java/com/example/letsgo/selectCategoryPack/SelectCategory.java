package com.example.letsgo.selectCategoryPack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsgo.Container;
import com.example.letsgo.R;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.auth.FirebaseAuth;

public class SelectCategory extends AppCompatActivity {

    private ImageView eventImgCategory,historicalImgCategory,resturantImgCategory,entertainmentImgCategory
            ,hotelsImgCategory,natureImgCategory;
   private  String adminId;
    private FirebaseAuth mAuth;
    TextView nav_title;
    String city;
    Toolbar toolbar;
    private SelecetCategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main);
        initlizeFields();
        setSupportActionBar(toolbar);
        nav_title.setText(city);
        eventImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Event",city);
            }
        });


        historicalImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Historical",city);

            }
        });

        entertainmentImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Entertainment",city);
            }
        });

        hotelsImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Hotels & Resorts",city);
            }
        });

        natureImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Nature",city);
            }
        });

        resturantImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToSelectedCategory("Cafe & Restaurant",city);
            }
        });

    }

    //connect id java with xml
    private void initlizeFields()
    {
        nav_title=findViewById(R.id.bar_title);
        eventImgCategory=findViewById(R.id.eventImgCategory);
        historicalImgCategory=findViewById(R.id.historicalImgCategory);
        resturantImgCategory=findViewById(R.id.resturantImgCategory);
        entertainmentImgCategory=findViewById(R.id.cinemaImgCategory);
        hotelsImgCategory=findViewById(R.id.hotelsImgCategory);
        natureImgCategory=findViewById(R.id.natureImgCategory);
        presenter=new SelecetCategoryPresenter(this);
        Intent i = getIntent();
         city = i.getStringExtra("city");
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
        Intent i=new Intent(SelectCategory.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
