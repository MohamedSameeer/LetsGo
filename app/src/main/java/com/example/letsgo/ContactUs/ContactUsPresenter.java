package com.example.letsgo.ContactUs;

import android.content.Context;
import android.os.AsyncTask;
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

import ai.api.AIServiceException;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public  class ContactUsPresenter {
    MessagesAdapter messagesAdapter;
    private FirebaseAuth mAuth;
    private static String Uid;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference mRef , botMsg;
    private DatabaseReference uIdRef;
private RecyclerView view;
    public static final String TAG = ContactUsPresenter.class.getName();
    //private Message messageObject;
    private List<Message> messageList;
    private Message currentMessage;
    Context context;
    final static String Aid = "owQrAb02Z7WJ2u0ER6uPnqoNZum2";

    ContactUsPresenter(RecyclerView view, Context context) {
        mAuth = FirebaseAuth.getInstance();
        Uid = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        uIdRef = firebaseDatabase.getReference().child("Messages").child(Uid);
        currentMessage = new Message();
        messageList = getMessageList();
        this.view=view;
        this.context=context;
        messagesAdapter=new MessagesAdapter(messageList, FirebaseAuth.getInstance().getCurrentUser().getUid());
        view.setLayoutManager(new LinearLayoutManager(context));
        view.setAdapter(messagesAdapter);

    }


 /*   public void handlingMessage(String message,AIDataService aiDataService,AIRequest aiRequest) {
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

        uploadMessageToFireBase(aiDataService,aiRequest);

    }*/

     static void uploadMessageToFireBase(final String message,final AIDataService aiDataService, final AIRequest aiRequest) {

        aiRequest.setQuery(message);
         mRef = firebaseDatabase.getReference().child("Messages").child(Uid).child(Aid).push();
         mRef.child("content").setValue(message);
         mRef.child("from").setValue(Uid);
         mRef.child("to").setValue(Aid);
         new AsyncTask<AIRequest,Void, AIResponse>(){

            @Override
            protected AIResponse doInBackground(AIRequest... aiRequests) {
                //final AIRequest request = aiRequests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest);
                    Log.e(TAG,"enter to class 2");
                    return response;
                } catch (AIServiceException e) {
                    Log.e(TAG,e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(AIResponse response) {
                if (response != null) {

                    Result result = response.getResult();
                    String reply = result.getFulfillment().getSpeech();

                    Log.e("speeeeeeech",reply);

                    botMsg=firebaseDatabase.getReference().child("Messages").child(Uid).child(Aid).push();
                    botMsg.child("content").setValue(reply);
                    botMsg.child("from").setValue("CustomerService");
                    botMsg .child("to").setValue(Uid);
//                                ChatMessage chatMessage = new ChatMessage(reply, "bot");
//                                ref.child("chat").push().setValue(chatMessage);
                }
            }
        }.execute(aiRequest);

    }

     List<Message> getMessageList() {
        final List<Message>message=new ArrayList<>();
         Log.e(TAG,"enter to class 2 getMessageList");
        uIdRef.child(Aid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                message.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                  /* currentMessage.setMessage(d.child("content").getValue().toString());
                    currentMessage.setFrom(d.child("from").getValue().toString());
                    currentMessage.setTo(d.child("to").getValue().toString());
                    messageList.add(currentMessage);*/


                  if( d.hasChild("content") && d.hasChild("from") && d.hasChild("to") ){
                      message.add(new Message(d.child("content").getValue().toString(),
                              d.child("from").getValue().toString()
                              , d.child("to").getValue().toString()));
                  }
               //Log.e(" presentermessage",currentMessage.getMessage());
                /*Log.e("presenter form",currentMessage.getMessage());
                Log.e("presenter to",currentMessage.getTo());*/



                }
                   // Log.e("main",messageList.get(i).getMessage());


                    messagesAdapter.notifyDataSetChanged();
                    view.smoothScrollToPosition(view.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        return message;

    }
}
