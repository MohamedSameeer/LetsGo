package com.example.letsgo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.Country.CountryActivity;
import com.example.letsgo.Event.EventFragment;
import com.example.letsgo.Favorite.Favorite;
import com.example.letsgo.Program.ProgramFragment;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.auth.FirebaseAuth;

public class DrawerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    private final static Fragment fCountry=new CountryActivity(),
            fFavorite=new Favorite();
    FirebaseAuth mAuth;
    int fragmentType;
    MenuItem menuItem;
    Toolbar toolbar;

    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);
         toolbar= findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();
        fragment = fCountry;


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        this.menuItem=menuItem;

        int id = menuItem.getItemId();

        switch (id)
        {
            case R.id.nav_home:
                fragment=fCountry;
                fragmentType=0;
                toolbar.setTitle("Home");
                break;
            case R.id.nav_events:
                fragment=new EventFragment();
                fragmentType=4;
                toolbar.setTitle("Event");
                break;
            case R.id.nav_save:
                fragment=fFavorite;
                fragmentType=1;
                toolbar.setTitle("Favorite");
                break;
            case R.id.nav_program:
                fragment=new ProgramFragment();
                fragmentType=2;
                toolbar.setTitle("Trips");
                break;

            case R.id.nav_contact_us:
                fragment=new ContactUsActivity();
                fragmentType=5;
                toolbar.setTitle("Contact us");
                break;
            case R.id.nav_logOut:
                fragmentType=3;
                mAuth.signOut();
                Intent i=new Intent(this, Splash.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(fragmentType!=0) {
                fragment = fCountry;
                toolbar.setTitle("Home");
                navigationView.getMenu().getItem(0).setChecked(true);
                fragmentType=0;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();

            }else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
