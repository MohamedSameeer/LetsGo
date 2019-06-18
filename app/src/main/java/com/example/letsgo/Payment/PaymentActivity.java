package com.example.letsgo.Payment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.letsgo.R;

public class PaymentActivity extends AppCompatActivity {
    EditText creditCard,name,cvv,numberOfTicket;
    Button confirm;
    Boolean isTrip,isEvent;
    String sName,sCredit,sCvv,nTicket,city,placeName,category;
    PaymentPresenter paymentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //"4012888888881881"
        initializeSpinners();
        initialize();

        Intent i=getIntent();
        city=i.getStringExtra("city");
        placeName=i.getStringExtra("placeName");
        category=i.getStringExtra("category");
        isTrip=i.getBooleanExtra("isTrip",false);
        isEvent=i.getBooleanExtra("isEvent",false);
        Log.e("PaymentActivity",isEvent+" "+isTrip);

        paymentPresenter=new PaymentPresenter(isTrip, isEvent);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName=name.getText().toString();
                sCredit=creditCard.getText().toString();
                sCvv=cvv.getText().toString();
                nTicket=numberOfTicket.getText().toString();
                boolean flag=true;
                if(sName.trim().isEmpty()){
                    name.setError("this field can't be empty");
                    name.requestFocus();
                    flag=false;
                }
                if(sCvv.trim().isEmpty()){
                    cvv.setError("this field can't be empty");
                    cvv.requestFocus();
                    flag=false;
                }
              if(sCredit.trim().isEmpty()){
                    creditCard.setError("this field can't be empty");
                    creditCard.requestFocus();
                    flag=false;
                }
               if(nTicket.trim().isEmpty()){
                    numberOfTicket.setError("this field can't be empty");
                   numberOfTicket.requestFocus();
                   flag=false;
                }
                if(flag){
                    if (paymentPresenter.verifyCreditCard(creditCard.getText().toString())) {
                        paymentPresenter.pushToFireBase(city,category,placeName);
                        numberOfTicket.setText("");
                        name.setText("");
                        cvv.setText("");
                        creditCard.setText("");

                    } else
                        creditCard.setError("this number is not valid");
                    creditCard.requestFocus();
                }
            }
        });

        Log.e("TotalPrice",""+totalPayment(9,10));

    }

    private int totalPayment(int numberOfTicket,int price){

        return numberOfTicket*price;
    }

    private void initialize(){
        numberOfTicket=findViewById(R.id.edit_number);
        name=findViewById(R.id.edit_full_name);
        cvv=findViewById(R.id.edit_cvv);
        confirm=findViewById(R.id.confirm);
        creditCard=findViewById(R.id.card_number_edit);
    }
    private void initializeSpinners() {
        Spinner month_spinner = (Spinner) findViewById(R.id.month);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter);

        Spinner year_spinner = (Spinner) findViewById(R.id.year);
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);
    }


}
