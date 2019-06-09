package com.example.letsgo.ContactUs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;

import com.example.letsgo.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class ContactUsActivity extends Fragment implements ai.api.AIListener {

    EditText messageBar;
    Button sendButton;
    ContactUsPresenter contactUsPresenter;
    String message,uid,aid;
    public static final String TAG = ContactUsActivity.class.getName();
    private AIService aiService;
    RecyclerView recyclerView;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_contact_us, container, false);
        //Dialogflow config
        Log.e(TAG,"enter to class 1");
        final AIConfiguration config = new AIConfiguration("87262c1dc1c244278746fe7a157c8fe5", AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(view.getContext(), config);
        aiService.setListener(this);
        final AIDataService aiDataService = new AIDataService(view.getContext(),config);
        final AIRequest aiRequest = new AIRequest();

        intializeFields();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = messageBar.getText().toString().trim();
                if (message.isEmpty()) {

                    messageBar.requestFocus();
                    messageBar.setError("you can't leave this field empty");

                } else {
                    ContactUsPresenter.uploadMessageToFireBase(message,aiDataService,aiRequest);
                }
                messageBar.setText("");
            }
        });

        return  view;
    }

    private void intializeFields() {

        messageBar = view.findViewById(R.id.chat_et);
        sendButton = view.findViewById(R.id.send_button);
        recyclerView =view.findViewById(R.id.contact_us_recyclerView);
        contactUsPresenter = new ContactUsPresenter(recyclerView,view.getContext());



    }

    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
