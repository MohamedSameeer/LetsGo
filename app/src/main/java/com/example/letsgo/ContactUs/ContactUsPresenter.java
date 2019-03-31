package com.example.letsgo.ContactUs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactUsPresenter {
    MessagesAdapter messagesAdapter;
    private FirebaseAuth mAuth;
    private String Uid, message;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private DatabaseReference uIdRef;
private RecyclerView view;
    private Message messageObject;
    private ArrayList<Message> messageList;
    private Message currentMessage;
    Context context;
    final static String Aid = "owQrAb02Z7WJ2u0ER6uPnqoNZum2";

    public ContactUsPresenter(RecyclerView view, Context context) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });
        Uid = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        uIdRef = firebaseDatabase.getReference().child("Messages").child(Uid).child(Aid);
        currentMessage = new Message();
        messageList = new ArrayList<>();
        this.view=view;
        this.context=context;
    }


    public void handlingMessage(String message) {
        this.message = message;
        messageObject = new Message();
        messageObject.setMessage(message);
        if (Uid.equals(Aid)) {
            messageObject.setFrom(Aid);
            messageObject.setTo(Uid);

        } else {
            messageObject.setFrom(Uid);
            messageObject.setTo(Aid);
        }

        uploadMessageToFireBase();

    }

    private void uploadMessageToFireBase() {
        mRef = firebaseDatabase.getReference().child("Messages").child(Uid).child(Aid).push();
        mRef.child("content").setValue(messageObject.getMessage());
        mRef.child("from").setValue(messageObject.getFrom());
        mRef.child("to").setValue(messageObject.getTo());


    }

    public List<Message> getMessageList() {
        uIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                  /* currentMessage.setMessage(d.child("content").getValue().toString());
                    currentMessage.setFrom(d.child("from").getValue().toString());
                    currentMessage.setTo(d.child("to").getValue().toString());
                    messageList.add(currentMessage);*/


                  if( d.hasChild("content") && d.hasChild("from") && d.hasChild("to") ){
                      messageList.add(new Message(d.child("content").getValue().toString(),
                              d.child("from").getValue().toString()
                              , d.child("to").getValue().toString()));
                  }
               //Log.e(" presentermessage",currentMessage.getMessage());
                /*Log.e("presenter form",currentMessage.getMessage());
                Log.e("presenter to",currentMessage.getTo());*/



                }


                   // Log.e("main",messageList.get(i).getMessage());

                    messagesAdapter=new MessagesAdapter(messageList, FirebaseAuth.getInstance().getCurrentUser().getUid(),"owQrAb02Z7WJ2u0ER6uPnqoNZum2");
                    view.setLayoutManager(new LinearLayoutManager(context));
                    view.setAdapter(messagesAdapter);
                    //messagesAdapter.notifyDataSetChanged();
                    view.smoothScrollToPosition(view.getAdapter().getItemCount());





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        return messageList;

    }



}
