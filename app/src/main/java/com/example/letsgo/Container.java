package com.example.letsgo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.Country.CountryActivity;
import com.example.letsgo.Favorite.Favorite;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.auth.FirebaseAuth;

public class Container extends AppCompatActivity {
    Fragment fragment;
    int flag;
    Toolbar toolbar;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        toolbar=findViewById(R.id.places_tool_bar);
        title=findViewById(R.id.bar_title);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();

        flag=intent.getIntExtra("flag",0);

        if(flag==1){
            fragment=new Favorite();
            title.setText("Favorite");
        }
        else {
            fragment = new ContactUsActivity();
            title.setText("Chat");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                fragment).commit();

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
        FirebaseAuth.getInstance().signOut();
        enterToSplash();
    }

    private void sendUserToContactUsActivity() {

        Intent i = new Intent (this, Container.class);
        i.putExtra("flag",2);
        startActivity(i);

    }
    private void enterToSplash(){
        Intent i=new Intent(Container.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
