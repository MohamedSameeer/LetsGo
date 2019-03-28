package com.example.letsgo.ContactUs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;

import com.example.letsgo.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ContactUsActivity extends AppCompatActivity {

    EditText messageBar;
    Button sendButton;
    ContactUsPresenter contactUsPresenter;
    String message,uid,aid;
    List<Message> messageList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        intializeFields();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = messageBar.getText().toString().trim();
                if (message.isEmpty()) {

                    messageBar.requestFocus();
                    messageBar.setError("you can't leave this field empty");

                } else {
                    contactUsPresenter.handlingMessage(message);
                }

                messageBar.setText("");


            }
        });


    }

    private void intializeFields() {

        messageBar = findViewById(R.id.chat_et);
        sendButton = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.contact_us_recyclerView);
        contactUsPresenter = new ContactUsPresenter(recyclerView,getApplicationContext());
        messageList=contactUsPresenter.getMessageList();


    }
}
