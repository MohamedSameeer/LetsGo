package com.example.letsgo.Reviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.letsgo.R;

public class ReviewActivity extends AppCompatActivity implements IActivity {

    EditText editText;
    Button button;
    ReviewPresenter reviewPresenter;;
    String placeName,placeCity,placeCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        intializeFields();
        getData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=editText.getText().toString();
                if (message.trim().isEmpty()) {

                    editText.requestFocus();
                    editText.setError("you can't leave this field empty");

                } else {
                    reviewPresenter.getReviewAndupLoadReViewToFirebase(getApplicationContext()
                            ,editText.getText().toString(),placeCity,placeCategory,placeName);
                    editText.setText("");
                    finish();
                }
            }
        });

    }

    private void getData() {
        Intent i=getIntent();
        placeName = i.getStringExtra("name");
        placeCity= i.getStringExtra("city");
        placeCategory= i.getStringExtra("category");
    }


    @Override
    public void intializeFields() {
        editText=findViewById(R.id.comment_et);
        button = findViewById(R.id.send_review);
        reviewPresenter=new ReviewPresenter();

    }


}
