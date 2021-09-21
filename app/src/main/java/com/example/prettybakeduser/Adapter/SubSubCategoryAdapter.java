package com.example.prettybakeduser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.prettybakeduser.ApiHelper.JsonField;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Model.SubSubCategory;
import com.example.prettybakeduser.Model.Subcategory;
import com.example.prettybakeduser.R;
import com.example.prettybakeduser.SubSubCategoryActivity;
import com.example.prettybakeduser.UserSessionManager;
import com.example.prettybakeduser.ViewCartActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static java.sql.Types.NULL;

public class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.ViewHolder> {
    Context context;
    UserSessionManager userSessionManager;
    ArrayList<SubSubCategory> listSubSubCategory;

    public SubSubCategoryAdapter(Context context, ArrayList<SubSubCategory> listSubSubCategory) {
        this.context = context;
        this.listSubSubCategory = listSubSubCategory;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.sub_sub_category_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubSubCategoryAdapter.ViewHolder holder, int position) {
        SubSubCategory subsubcategory=listSubSubCategory.get(position);
        String name=subsubcategory.getSub_sub_category_name();
        String price=subsubcategory.getSub_sub_price();
        String id=subsubcategory.getSub_sub_category_id();

        holder.tvSubSubCategory.setText(name);
        holder.tvSubSubCategoryPrice.setText(price);
        Log.d("id",id);
       Glide.with(context).load(WebUrl.KEY_IMAGE_URL1+subsubcategory.getSub_sub_category_image()).into(holder.ivSubSubCatimage);
        holder.btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSessionManager=new UserSessionManager(context);
                sendDataToDatabase(subsubcategory.getSub_sub_category_name(),subsubcategory.getSub_sub_price(),subsubcategory.getSub_sub_category_image(),subsubcategory.getSub_sub_category_id());
            }
        });
    }

    private void sendDataToDatabase(String sub_sub_category_name, String sub_sub_price, String sub_sub_category_image, String sub_category_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.KEY_ORDER_INSERT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseDataBaseResponse(response);
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
                params.put(JsonField.USERID,userSessionManager.getUSERID());
                params.put(JsonField.SUBCATEGORY_ID,sub_category_id);
                params.put(JsonField.SUBCATEGORY_NAME,sub_sub_category_name);
                params.put(JsonField.SUBCATEGORY_IMAGE,sub_sub_category_image);
                params.put(JsonField.PRICE,sub_sub_price);

                SharedPreferences sp=context.getSharedPreferences("CartUnique",MODE_PRIVATE);
                int value=sp.getInt("UniqueNo",NULL);
                Log.d("AddtoCartvalue",String.valueOf(value));
                params.put(JsonField.CartId,String.valueOf(value));
                Log.d("Id1",sub_category_id);
                Log.d("name1",sub_sub_category_name);
                Log.d("image1",sub_sub_category_image);
                Log.d("price1",sub_sub_price);
                Log.d("userid",userSessionManager.getUSERID());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void parseDataBaseResponse(String response) {
        Log.d("Response",response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            int flag=jsonObject.optInt(JsonField.FLAG);
            String message=jsonObject.optString(JsonField.MESSAGES);
            if(flag==1)
            {
                Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show();
                 String name= jsonObject.optString(JsonField.ProductName);
                String image= jsonObject.optString(JsonField.ProductImage);
                String price= jsonObject.optString(JsonField.ProductPrice);

                Intent intent=new Intent(context, ViewCartActivity.class);

                intent.putExtra("name",name);
                intent.putExtra("image",image);
                intent.putExtra("price",price);
                intent.putExtra("quantity","1");
                Log.d("putextraimage",image);
                Log.d("putextraprie",price);
                Log.d("putextraname",name);

                context.startActivity(intent);
//                context.finish();
            }
            else
            {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listSubSubCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivSubSubCatimage;
        TextView tvSubSubCategory,tvSubSubCategoryPrice;
        Button btnaddtocart;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivSubSubCatimage=itemView.findViewById(R.id.ivSubSubCatimage);
            tvSubSubCategory=itemView.findViewById(R.id.tvSubSubCategory);
            tvSubSubCategoryPrice=itemView.findViewById(R.id.tvSubSubCategoryPrice);
            btnaddtocart=itemView.findViewById(R.id.btnaddtocart);
        }
    }
}
