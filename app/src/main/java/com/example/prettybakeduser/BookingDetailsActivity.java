package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.Adapter.BookingHistoryCatAdapter;
import com.example.prettybakeduser.Adapter.CategoryAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Listener.BookingDetailsCatClickListener;
import com.example.prettybakeduser.Model.BookingHistoryCat;
import com.example.prettybakeduser.Model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingDetailsActivity extends AppCompatActivity implements BookingDetailsCatClickListener {
RecyclerView rvCatDisplay;
ArrayList<BookingHistoryCat> listbookingHistory;
UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        userSessionManager=new UserSessionManager(BookingDetailsActivity.this);
        rvCatDisplay=findViewById(R.id.rvCatDisplay);
        rvCatDisplay.setHasFixedSize(true);

        getCategory();
    }

    private void getCategory() {
        listbookingHistory = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_ORDER_DETAILS_CAT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
               // params.put(JsonField.USERID, userSessionManager.getUSERID());
                params.put(JsonField.USERID, "1");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseJson(String response) {
        try {
        JSONObject jsonObject=new JSONObject(response);
        int flag=jsonObject.optInt(JsonField.FLAG);
            Log.d("REsponse",response);
        if(flag==1)
        {
            JSONArray jsonArray=jsonObject.getJSONArray(JsonField.BOOKING_HISTORY_ARRAY);
            if(jsonArray.length()>0)
            {
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.optJSONObject(i);
                    String totalamount=object.getString(JsonField.TotalAmount);
                    String totalitems=object.optString(JsonField.TotalItems);
                    String cart_id=object.optString(JsonField.CartId);

                    Log.d("Response",JsonField.BOOKING_HISTORY_ARRAY);
                    BookingHistoryCat category=new BookingHistoryCat();
                    category.setCart_id(cart_id);
                    category.setTotal_amount(totalamount);
                    category.setTotal_items(totalitems);
                    listbookingHistory.add(category);
                }
                BookingHistoryCatAdapter categoryAdapter=new BookingHistoryCatAdapter(BookingDetailsActivity.this,listbookingHistory);
                categoryAdapter.setBookingDetailsCatClickListener(BookingDetailsActivity.this);
                rvCatDisplay.setAdapter(categoryAdapter);
            }
        }
    }catch (
    JSONException e){
        e.getStackTrace();
    }

    }

    @Override
    public void setOnItemClicked(ArrayList<BookingHistoryCat> listCategory, int position) {
        Intent intent=new Intent(BookingDetailsActivity.this,BookingHistorySubCatActivity.class);
        BookingHistoryCat category=listCategory.get(position);
        String id=category.getCart_id();
        String amount=category.getTotal_amount();

        intent.putExtra(JsonField.CartId,id);
        intent.putExtra(JsonField.TotalAmount,amount);

        startActivity(intent);
    }
}