package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText etemail,etpassword;
    Button btnLogin;
    TextView tvforgetpass,tvsignup;
    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        tvforgetpass=findViewById(R.id.forgetpass);
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpass);
        btnLogin=findViewById(R.id.btnLogin);
        tvsignup=findViewById(R.id.tvsignup);
        userSessionManager=new UserSessionManager(LoginActivity.this);
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ResigrationActivity.class);
                startActivity(intent);
            }
        });
        tvforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,HomeScreen.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckUserName() && CheckUserPass()){
                    sendLoginRequest();
                }
            }
        });
    }

    private void sendLoginRequest() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseLoginResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put(JsonField.USEREMAIl,etemail.getText().toString());
                params.put(JsonField.USERPASSWORD,etpassword.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseLoginResponse(String response) {
        try {
            Log.d("REsponse",response);
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            if(flag==1)
            {
                Log.d("Tag","Login Success");
                JSONObject jsonArray=jsonObject.optJSONObject(JsonField.USER_ARRAY);
                Log.d("Response",JsonField.USER_ARRAY);
                String userId=jsonArray.optString(JsonField.USERID);
                String username=jsonArray.optString(JsonField.USERNAME);
                String useremail=jsonArray.optString(JsonField.USEREMAIl);
                String usergender=jsonArray.optString(JsonField.USERGENDER);
                String usermobile=jsonArray.optString(JsonField.USERMOBILE);
                String useraddress=jsonArray.optString(JsonField.USERAddress);

                userSessionManager.setLoginStatus();
                userSessionManager.setUserDetails(userId,username,useremail,usergender,usermobile,useraddress);
               Intent intent=new Intent(LoginActivity.this,CityActivity.class);
               finish();
               startActivity(intent);
            }
        }catch (JSONException e){
            e.getStackTrace();
        }
    }

    private boolean CheckUserName(){
        boolean isUserNameValid=false;
        String uemail=etemail.getText().toString().trim();
        Log.d("email",uemail);
        if(etemail.getText().toString().trim().length()<=0){
            etemail.setError("enter email");
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
            isUserNameValid=true;
        }
        else{
            etemail.setError("enter correct email ");
        }
        return isUserNameValid;
    }
    private boolean CheckUserPass(){
        boolean isUserPassValid=false;
        String pass=etpassword.getText().toString().trim();
        Log.d("pass",pass);
        if(etpassword.getText().toString().trim().length()<=0){
            etpassword.setError("enter password");
        }
        else{
            isUserPassValid=true;
        }
        return isUserPassValid;
    }
}
