package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BookingDActivity extends AppCompatActivity {
TextView tvNameS,tvMobS,tvtotalamount;
EditText etAddS;
UserSessionManager userSessionManager;
Button btnplaceorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_dactivity);

        tvNameS=findViewById(R.id.tvName);
        tvMobS=findViewById(R.id.tvMobS);
        tvtotalamount=findViewById(R.id.tvtotalamount);
        etAddS=findViewById(R.id.etAddS);
        btnplaceorder=findViewById(R.id.btnplaceorder);

        userSessionManager=new UserSessionManager(BookingDActivity.this);

        Intent intent=getIntent();
       // String amount=intent.getStringExtra("totalamount");
       // tvtotalamount.setText(amount);
        tvNameS.setText(userSessionManager.getUSERNAME());
        tvMobS.setText(userSessionManager.getUSERMOBILENO());
        etAddS.setText(userSessionManager.getUSERADDRESS());



    }
}