package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.RegistrationManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResigrationActivity<RegistrationActivity> extends AppCompatActivity {
 EditText etuser_name,etuser_email,etuser_password,etuser_address,etuser_mobile;
 Button btnSignup;
 RadioButton rbfemale,rbmale;
 private String selectgender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_resigration);

        etuser_name=findViewById(R.id.etuser_name);
        etuser_email=findViewById(R.id.etuser_email);
        etuser_address=findViewById(R.id.etuser_address);
        etuser_mobile=findViewById(R.id.etuser_mobile);
        etuser_password=findViewById(R.id.etuser_password);
        rbfemale=findViewById(R.id.rbfemale);
        rbmale=findViewById(R.id.rbmale);
        btnSignup=findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(CheckUserName() && CheckUserEmail() && CheckUserGender() && CheckUserMobile() && CheckUserPassword()){
                        sendSignupRequest();
                        selectgender=rbfemale.isChecked()?"Female":"Male";
                   }
            }
        });
    }

    private void sendSignupRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_REGISTRATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseSignUpResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.d("Inside method","inside params");
                params.put(JsonField.USERNAME,etuser_name.getText().toString());
                params.put(JsonField.USEREMAIl,etuser_email.getText().toString());
                params.put(JsonField.USERAddress,etuser_address.getText().toString());
                params.put(JsonField.USERPASSWORD,etuser_password.getText().toString());
                params.put(JsonField.USERMOBILE,etuser_mobile.getText().toString());
                params.put(JsonField.USERGENDER,selectgender);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ResigrationActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseSignUpResponse(String response) {
        Log.d("Response",response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String message=jsonObject.optString(JsonField.MESSAGES);
            if(flag==1)
            {
                Toast.makeText(this, "Succes", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ResigrationActivity.this, HomeScreenMainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean CheckUserName() {
        boolean isUserNameValid=false;
        if(etuser_name.getText().toString().trim().length()<=0)
        {
            etuser_name.setError("Enter Name");
        }
        else
            isUserNameValid=true;

    return isUserNameValid;
    }
    private boolean CheckUserEmail(){
        boolean isUserEmailValid=false;
        String etu_email=etuser_email.getText().toString().trim();
        if(etuser_email.getText().toString().trim().length()<=0)
        {
            etuser_email.setError("Enter Email");
        }else if(Patterns.EMAIL_ADDRESS.matcher(etu_email).matches()){
            isUserEmailValid=true;
        }
        else{
            etuser_email.setError("Enter Correct Email");
        }
        return isUserEmailValid;
    }
    private boolean CheckUserPassword(){
        boolean isUserPasswordValid=false;
        if(etuser_password.getText().toString().trim().length()<=0)
        {
            etuser_password.setError("Enter Password");
        }
        else{
            isUserPasswordValid=true;
        }
        return isUserPasswordValid;
    }
    private boolean CheckUserGender(){
        boolean isUserGenderValid=false;
        isUserGenderValid=rbfemale.isChecked()|| rbmale.isChecked();
        if(isUserGenderValid==false){
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
        }
        return isUserGenderValid;
    }
    private boolean CheckUserMobile(){
        boolean isUserMobileValid=false;
        if(etuser_mobile.getText().toString().trim().length()<=0)
        {
            etuser_mobile.setError("Enter Mobile");
        }
        else if(etuser_mobile.getText().toString().trim().length()==10) {
            isUserMobileValid=true;
        }
        else{
            etuser_mobile.setError("Enter Correct No.");
        }
        return isUserMobileValid;
    }
}