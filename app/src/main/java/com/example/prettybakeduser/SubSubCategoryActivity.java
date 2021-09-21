package com.example.prettybakeduser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prettybakeduser.Adapter.SubSubCategoryAdapter;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Model.SubSubCategory;
import com.example.prettybakeduser.Model.Subcategory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubSubCategoryActivity extends AppCompatActivity {
    RecyclerView rvSubSubCategory;
    ArrayList<SubSubCategory> listSubSubCategory;
    private  String id;
    private  String name,image,price;
    Button btnaddtocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sub_category);
        rvSubSubCategory=findViewById(R.id.rvSubSubCategory);
        btnaddtocart=findViewById(R.id.btnaddtocart);

        rvSubSubCategory.setHasFixedSize(true);

        listSubSubCategory=new ArrayList<SubSubCategory>();
        Intent intent=getIntent();
        id=intent.getStringExtra(JsonField.SUBCATEGORY_ID);
        name=intent.getStringExtra(JsonField.SUBCATEGORY_NAME);
        image=intent.getStringExtra(JsonField.SUBCATEGORY_IMAGE);
        price=intent.getStringExtra(JsonField.PRICE);

        Log.d("id",id);
        Log.d("name",name);
        Log.d("image",image);
        Log.d("price",price);

        SubSubCategory subSubCategory=new SubSubCategory();
        subSubCategory.setSub_sub_category_id(id);
        subSubCategory.setSub_sub_category_name(name);
        subSubCategory.setSub_sub_category_image(image);
        subSubCategory.setSub_sub_price(price);

        listSubSubCategory.add(subSubCategory);
        SubSubCategoryAdapter subSubCategoryAdapter=new SubSubCategoryAdapter(SubSubCategoryActivity.this,listSubSubCategory);
        rvSubSubCategory.setAdapter(subSubCategoryAdapter);


    }



}