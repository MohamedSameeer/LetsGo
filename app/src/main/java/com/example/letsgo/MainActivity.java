package com.example.letsgo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.letsgo.Adminstrator.PushingData;
import com.example.letsgo.ContactUs.ContactUsActivity;
import com.example.letsgo.HomeFragment.HomeFragment;
import com.example.letsgo.Splash.Splash;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    String adminId;
    FirebaseAuth mAuth;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        initialization();
        setSupportActionBar(myToolbar);


    }

    //
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

        if (user!=null){
            String userId=user.getUid();
            if(userId.equals(adminId)){
                Intent i=new Intent(MainActivity.this, PushingData.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }else{
            enterToSplash();
        }
    }
    public void initialization(){
        mAuth=FirebaseAuth.getInstance();
        adminId="owQrAb02Z7WJ2u0ER6uPnqoNZum2";
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

    }
    private void enterToSplash(){
        Intent i=new Intent(MainActivity.this, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment=new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    //TODO fragment intial change return to break
                    return true;
                case R.id.navigation_notifications:
                    //TODO fragment intial change return to break
                    return true;
                default:
                    selectedFragment=new HomeFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return false;
        }
    };

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
}

    private void sendUserToContactUsActivity() {

        Intent i = new Intent (this, ContactUsActivity.class);
        startActivity(i);

    }
}
