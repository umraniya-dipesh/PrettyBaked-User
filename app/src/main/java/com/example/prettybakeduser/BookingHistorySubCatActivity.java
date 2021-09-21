package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.Adapter.BookingHistorySubCatAdapter;
import com.example.prettybakeduser.Adapter.SubCategoryAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Model.BookingHistorySubCat;
import com.example.prettybakeduser.Model.Subcategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistorySubCatActivity extends AppCompatActivity {
RecyclerView rvSubCatBooking;
ArrayList<BookingHistorySubCat> listsubcat;
private  String id,amount;
TextView tvtotalprice;
UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_sub_cat);

        rvSubCatBooking=findViewById(R.id.rvSubCatBooking);
        userSessionManager=new UserSessionManager(BookingHistorySubCatActivity.this);
        Intent intent=getIntent();
        id=intent.getStringExtra(JsonField.CartId);
        amount=intent.getStringExtra(JsonField.TotalAmount);

        tvtotalprice=findViewById(R.id.tvtotalprice);
        tvtotalprice.setText(amount);
        Log.d("id",id);
        getSubCategory(id);
    }

    private void getSubCategory(String id) {
        listsubcat=new ArrayList<BookingHistorySubCat>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_ORDER_DETAILS_SUBCAT_URL, new Response.Listener<String>() {
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
               // map.put(JsonField.USERID,userSessionManager.getUSERID());
                map.put(JsonField.USERID,"1");

                map.put(JsonField.CartId,id);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(BookingHistorySubCatActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseJson(String response) {
        try {
            Log.d("response",response);
            JSONObject jsonObject= new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            if(flag==1)
            {
                JSONArray jsonArray=jsonObject.getJSONArray(JsonField.BOOKING_HISTORY_SUBCATARRAY);
                if(jsonArray.length()>0)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.optJSONObject(i);
                        String subcategoryName=object.optString(JsonField.SUBCATEGORY_NAME);
                        String subcategoryImage=object.optString(JsonField.SUBCATEGORY_IMAGE);
                        String subcategoryPrice=object.optString(JsonField.PRICE);
                        Log.d("subname",subcategoryName);
                        BookingHistorySubCat subcategory= new BookingHistorySubCat();
                        subcategory.setSub_category_name(subcategoryName);
                        subcategory.setSub_category_image(subcategoryImage);
                        subcategory.setPrice(subcategoryPrice);

                        listsubcat.add(subcategory);
                    }
                    BookingHistorySubCatAdapter subcategoryAdapter=new BookingHistorySubCatAdapter(BookingHistorySubCatActivity.this,listsubcat);
                    rvSubCatBooking.setAdapter(subcategoryAdapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}