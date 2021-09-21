package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.Adapter.CategoryAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Listener.CategoryItemClickListener;
import com.example.prettybakeduser.Model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity implements CategoryItemClickListener {
RecyclerView rvCategory;
    ViewFlipper flipper;
ArrayList<Category> listCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        rvCategory=findViewById(R.id.rvCategory);
        flipper=findViewById(R.id.flipper);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();
        int img_flip[]={R.drawable.off1,R.drawable.off3,R.drawable.off4,R.drawable.off6};
        flipper=findViewById(R.id.flipper);
        for (int image:img_flip)
        {
            flipperimage(image);
        }
        getCategory();
    }
    private void flipperimage(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
    }
    private void getCategory() {
        listCategory=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(HomeScreen.this);

        requestQueue.add(stringRequest);
    }
    private void parseJson(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            if(flag==1)
            {
                JSONArray jsonArray=jsonObject.getJSONArray(JsonField.CATEGORY_ARRAY);
                if(jsonArray.length()>0)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.optJSONObject(i);
                        String categoryId=object.getString(JsonField.CATEGORY_ID);
                        String categoryName=object.optString(JsonField.CATEGORY_NAME);
                        String categoryImage=object.optString(JsonField.CATEGORY_IMAGE);

                        Category category=new Category();
                        category.setCategory_id(categoryId);
                        category.setCategory_name(categoryName);
                        category.setCategory_image(categoryImage);
                        listCategory.add(category);
                    }
                    CategoryAdapter categoryAdapter=new CategoryAdapter(HomeScreen.this,listCategory);
                   // categoryAdapter.setCategoryItemClickListener(HomeScreen.this);
                    rvCategory.setAdapter(categoryAdapter);
                }
            }
        }catch (JSONException e){
            e.getStackTrace();
        }
    }

    @Override
    public void setOnItemClicked(ArrayList<Category> listCategory, int position) {
        Intent intent=new Intent(HomeScreen.this,SubcategoryActivity.class);
        Category category=listCategory.get(position);
        String id=category.getCategory_id();
        String name=category.getCategory_name();
        intent.putExtra(JsonField.CATEGORY_ID,id);
        intent.putExtra(JsonField.CATEGORY_NAME,name);
        startActivity(intent);
    }
}