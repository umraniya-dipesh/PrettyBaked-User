package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.Adapter.SubCategoryAdapter;
import com.example.prettybakeduser.Adapter.SubSubCategoryAdapter;
import com.example.prettybakeduser.Adapter.ViewCartAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Model.SubSubCategory;
import com.example.prettybakeduser.Model.Subcategory;
import com.example.prettybakeduser.Model.ViewCart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.NULL;

public class ViewCartActivity extends AppCompatActivity {
RecyclerView rv_viewcart;
    private  String name,image,price,quantity;
    TextView tvCartAmount;
    Button btnCheckout;
    ArrayList<ViewCart> listcart;
    UserSessionManager userSessionManager;
    LinearLayout llshow,ll_checkout;
    Button btnhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        rv_viewcart = findViewById(R.id.rv_viewcart);
        rv_viewcart.setHasFixedSize(true);
        tvCartAmount=findViewById(R.id.tvCartAmount);
        btnCheckout = findViewById(R.id.btnCheckout);
        llshow=findViewById(R.id.llshow);
        btnhome=findViewById(R.id.btnhome);
        ll_checkout=findViewById(R.id.ll_checkout);


        userSessionManager=new UserSessionManager(ViewCartActivity.this);
        SharedPreferences sp=this.getSharedPreferences("CartUnique",MODE_PRIVATE);
        int value=sp.getInt("UniqueNo",NULL);
        Log.d("value",String.valueOf(value));

        setCartDetails();
    }

    private void setCartDetails() {
        listcart=new ArrayList<ViewCart>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_VIEW_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showcartvalues(response.toString());
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
        RequestQueue requestQueue= Volley.newRequestQueue(ViewCartActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showcartvalues(String response) {
        try {
            Log.d("response",response);
            JSONObject jsonObject= new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String totalamount=jsonObject.optString(JsonField.TOTAL_CART_PRICE);
            tvCartAmount.setText("₹ "+totalamount);

            btnCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewCartActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ViewCartActivity.this,ShippingActivity.class);
                  intent.putExtra("totalamount",totalamount);
                    Log.d("totalamount", (totalamount));
                   startActivity(intent);
                }
            });
            if(flag==1)
            {
                JSONArray jsonArray=jsonObject.getJSONArray(JsonField.VIEWCART_ARRAY);
                if(jsonArray.length()>0)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.optJSONObject(i);
                        String subcategoryId=object.getString(JsonField.SUBCATEGORY_ID);
                        String subcategoryName=object.optString(JsonField.SUBCATEGORY_NAME);
                        String subcategoryImage=object.optString(JsonField.SUBCATEGORY_IMAGE);
                        String subcategoryPrice=object.optString(JsonField.PRICE);
                        String orderId=object.optString(JsonField.ORDER_ID);



                        ViewCart viewCart= new ViewCart();
                        viewCart.setTvCatName(subcategoryName);
                        viewCart.setCartimgview(subcategoryImage);
                        viewCart.setTvCatPrice(subcategoryPrice);
                        viewCart.setOrder_id(orderId);


                        listcart.add(viewCart);
                    }
                    ViewCartAdapter viewCartAdapter =new ViewCartAdapter(ViewCartActivity.this,listcart);
                     rv_viewcart.setAdapter(viewCartAdapter);
                }

            }
            else
            {
                llshow.setVisibility(View.VISIBLE);
                ll_checkout.setVisibility(View.GONE);
                btnhome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ViewCartActivity.this,HomeScreenMainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
