package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.prettybakeduser.Adapter.Mycityadapter;
import com.example.prettybakeduser.Model.city;

public class CityActivity extends AppCompatActivity {
    private RecyclerView cityl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city); city[] citylist = new city[10];

        citylist[0]=new city("Ahmedabad",R.drawable.amd1);
        citylist[1]=new city("Delhi",R.drawable.del);
        citylist[2]=new city("Kolkata", R.drawable.kol1);
        citylist[3]=new city("Banglore",R.drawable.ban);
        citylist[4]=new city("Surat",R.drawable.srt);
        citylist[5]=new city("Udaipur",R.drawable.udaipur);
        citylist[6]=new city("Hyderabad",R.drawable.hyd);
        citylist[7]=new city("Jaipur",R.drawable.jaipur);
        citylist[8]=new city("Mumbai",R.drawable.mum);
        citylist[9]=new city("Chennai",R.drawable.che);

        Mycityadapter miad = new Mycityadapter();

        miad.setItems(citylist);
        cityl=findViewById(R.id.rcvlist);
        cityl.setAdapter(miad);
    }
}