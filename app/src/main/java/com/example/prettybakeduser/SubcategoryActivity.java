package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.prettybakeduser.Adapter.CategoryAdapter;
import com.example.prettybakeduser.Adapter.SubCategoryAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Listener.SubCategoryItemClickListener;
import com.example.prettybakeduser.Model.Category;
import com.example.prettybakeduser.Model.Subcategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubcategoryActivity extends AppCompatActivity implements SubCategoryItemClickListener {
    RecyclerView rvSubCategory;
    ArrayList<Subcategory> listSubCategory;
    private  String id;
    private  String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);
        rvSubCategory=findViewById(R.id.rvSubCategory);
        rvSubCategory.setHasFixedSize(true);
        Intent intent=getIntent();
        id=intent.getStringExtra(JsonField.CATEGORY_ID);
        name=intent.getStringExtra(JsonField.CATEGORY_NAME);
        Log.d("id",id);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SubcategoryActivity.this);
//        rvSubCategory.setLayoutManager(linearLayoutManager);
        getSubCategory(id);
    }

    private void getSubCategory(String id) {
        listSubCategory=new ArrayList<Subcategory>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_SUB_CATEGORY_URL, new Response.Listener<String>() {
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
                map.put(JsonField.CATEGORY_ID,id);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(SubcategoryActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseJson(String response) {
        try {
            Log.d("response",response);
            JSONObject jsonObject= new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            if(flag==1)
            {
                JSONArray jsonArray=jsonObject.getJSONArray(JsonField.SUBCATEGORY_ARRAY);
                if(jsonArray.length()>0)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.optJSONObject(i);
                        String subcategoryId=object.getString(JsonField.SUBCATEGORY_ID);
                        String subcategoryName=object.optString(JsonField.SUBCATEGORY_NAME);
                        String subcategoryImage=object.optString(JsonField.SUBCATEGORY_IMAGE);
                        String subcategoryPrice=object.optString(JsonField.PRICE);

                        Subcategory subcategory= new Subcategory();
                        subcategory.setSub_category_id(subcategoryId);
                        subcategory.setSub_category_name(subcategoryName);
                        subcategory.setSub_category_image(subcategoryImage);
                        subcategory.setPrice(subcategoryPrice);

                        listSubCategory.add(subcategory);
                    }
                    SubCategoryAdapter subcategoryAdapter=new SubCategoryAdapter(SubcategoryActivity.this,listSubCategory);
                    subcategoryAdapter.setSubcategoryItemClickListener(SubcategoryActivity.this);
                    rvSubCategory.setAdapter(subcategoryAdapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnItemClicked(ArrayList<Subcategory> listSubCategory, int position) {
        Intent intent=new Intent(SubcategoryActivity.this,SubSubCategoryActivity.class);
        Subcategory subcategory=listSubCategory.get(position);
        String id=subcategory.getSub_category_id();
        String name=subcategory.getSub_category_name();
        String image=subcategory.getSub_category_image();
        String price=subcategory.getPrice();

        Log.d("image",image);
        intent.putExtra(JsonField.SUBCATEGORY_ID,id);
        intent.putExtra(JsonField.SUBCATEGORY_NAME,name);
        intent.putExtra(JsonField.SUBCATEGORY_IMAGE,image);
        intent.putExtra(JsonField.PRICE,price);
        startActivity(intent);
    }
}