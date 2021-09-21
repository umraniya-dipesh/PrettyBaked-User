package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.Adapter.ViewCartAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Model.ViewCart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShippingActivity extends AppCompatActivity {

    TextView tvNameS,tvMobS,tvtotalamount;
    EditText etAddS;
    UserSessionManager userSessionManager;
    String amount;
    Button btnplaceorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        tvNameS=findViewById(R.id.tvNameS);
        tvMobS=findViewById(R.id.tvMobS);
        tvtotalamount=findViewById(R.id.tvtotalamount);
        etAddS=findViewById(R.id.etAddS);
        btnplaceorder=findViewById(R.id.btnplaceorder);

        userSessionManager=new UserSessionManager(ShippingActivity.this);

        Log.d("name",userSessionManager.getUSERNAME());
        Log.d("mob",userSessionManager.getUSERADDRESS());
        Log.d("add",userSessionManager.getUSERMOBILENO());

        String name=userSessionManager.getUSERNAME();
        String mobileno=userSessionManager.getUSERMOBILENO();
        String address=userSessionManager.getUSERADDRESS();

        Intent intent=getIntent();
        amount=intent.getStringExtra("totalamount");
         tvtotalamount.setText(amount);
       tvMobS.setText(mobileno);
       tvNameS.setText(name);
       etAddS.setText(address);

       btnplaceorder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                    sendBookingData();

           }
       });

    }

    private void sendBookingData() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_CLEAR_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String>map=new HashMap<>();
                map.put(JsonField.USERID,userSessionManager.getUSERID());

                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ShippingActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseJson(String response) {
        try {
            Log.d("response",response);
            JSONObject jsonObject= new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            if(flag==1)
            {
                btnplaceorder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        Intent intent=new Intent(ShippingActivity.this,SuccesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}