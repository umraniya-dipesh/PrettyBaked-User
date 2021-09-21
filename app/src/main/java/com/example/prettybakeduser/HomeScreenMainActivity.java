package com.example.prettybakeduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
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
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class HomeScreenMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CategoryItemClickListener {
    private NavigationView navigationView;
    RecyclerView rvCategory;
    ViewFlipper flipper;
    ArrayList<Category> listCategory;
    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvCategory=findViewById(R.id.rvCategory);
        userSessionManager=new UserSessionManager(HomeScreenMainActivity.this);

        SharedPreferences sp =this.getSharedPreferences("CartUnique",MODE_PRIVATE);
        Random r = new Random();
        int min = 65;
        int max = 80;
        int i1 = r.nextInt(max - min + 1) + min;
        SharedPreferences.Editor speditor=sp.edit();
        speditor.putInt("UniqueNo",i1);
        Log.d("valuehome",String.valueOf(i1));

        speditor.apply();
//        flipper=findViewById(R.id.flipper);
//        flipper.setFlipInterval(3000);
//        flipper.startFlipping();
//        int img_flip[]={R.drawable.off1,R.drawable.off3,R.drawable.off4,R.drawable.off6};
//        flipper=findViewById(R.id.flipper);
//        for (int image:img_flip)
//        {
//            flipperimage(image);
//        }
        if (userSessionManager.getLoginStatus())
        {
            //  Toast.makeText(this, "Welcome User", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(HomeScreenMainActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle =new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toogle.setDrawerIndicatorEnabled(false);
        final Drawable drawable= ResourcesCompat.getDrawable(getResources(),R.drawable.ic_menu,null);
        toogle.setHomeAsUpIndicator(drawable);

        toogle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerVisible(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getCategory();
    }
//    private void flipperimage(int image) {
//        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource(image);
//        flipper.addView(imageView);
//        flipper.setFlipInterval(3000);
//        flipper.setAutoStart(true);
//        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
//        flipper.setInAnimation(this, android.R.anim.slide_in_left);
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // MenuInflater menuInflater=getMenuInflater();
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_cart:
                Intent intent2 = new Intent(this, ViewCartActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_home:
                Intent i = new Intent(this, HomeScreenMainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.nav_aboutus:
                Intent i1 = new Intent(this, AboutUsActivity.class);
                startActivity(i1);
                break;

            case R.id.nav_log:
                userSessionManager.logout();
                Intent i2 = new Intent(this, LoginActivity.class);
                finish();
                startActivity(i2);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        RequestQueue requestQueue= Volley.newRequestQueue(HomeScreenMainActivity.this);

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
                    CategoryAdapter categoryAdapter=new CategoryAdapter(HomeScreenMainActivity.this,listCategory);
                    categoryAdapter.setCategoryItemClickListener(HomeScreenMainActivity.this);
                    rvCategory.setAdapter(categoryAdapter);
                }
            }
        }catch (JSONException e){
            e.getStackTrace();
        }
    }

    @Override
    public void setOnItemClicked(ArrayList<Category> listCategory, int position) {
        Intent intent=new Intent(HomeScreenMainActivity.this,SubcategoryActivity.class);
        Category category=listCategory.get(position);
        String id=category.getCategory_id();
        String name=category.getCategory_name();
        intent.putExtra(JsonField.CATEGORY_ID,id);
        intent.putExtra(JsonField.CATEGORY_NAME,name);

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
