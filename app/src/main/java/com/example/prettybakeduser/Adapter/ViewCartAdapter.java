package com.example.prettybakeduser.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.prettybakeduser.Model.ViewCart;
import com.example.prettybakeduser.R;
import com.example.prettybakeduser.ViewCartActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewCartAdapter extends RecyclerView.Adapter<ViewCartAdapter.ViewHolder> {
    ArrayList<ViewCart> listcart;
    Context context;

    public ViewCartAdapter(Context context,ArrayList<ViewCart> listcart ) {
        this.listcart = listcart;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.view_order_row_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewCartAdapter.ViewHolder holder, int position) {
        ViewCart viewCart = listcart.get(position);
        String catname=viewCart.getTvCatName();
        String price=viewCart.getTvCatPrice();
        String catquantity=viewCart.getCatquanitity();
        String orderid=viewCart.getOrder_id();
        Log.d("orderid",orderid);

        holder.tvCatName.setText(catname);
        holder.tvCatPrice.setText(price);
        //holder.catquanitity.setText(catquantity);

        Glide.with(context).load(WebUrl.KEY_IMAGE_URL1+viewCart.getCartimgview()).into(holder.cartimgview);

        holder.iVRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRemovefromCartAlert(viewCart.getOrder_id());
            }
        });
    }

    private void ShowRemovefromCartAlert(final String order_id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Please Confirm");
        builder1.setMessage("Are you sure want to remove this item from cart?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                         DeleteCartItem(order_id);
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void DeleteCartItem( String order_id) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, WebUrl.KEY_DELETE_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                RemoveCartJSON(response);
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
                map.put(JsonField.ORDER_ID,order_id);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void RemoveCartJSON(String response) {
        Log.d("RESPONSE", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonField.FLAG);
            String Message = jsonObject.optString(JsonField.MESSAGES);
            if (flag == 1) {
                Intent i = new Intent(context, ViewCartActivity.class);
                context.startActivity(i);
                ((ViewCartActivity)context).finish();
            }else {
                Toast.makeText(context, Message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return listcart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartimgview,iVRemoveFromCart;
        TextView tvCatName,tvCatPrice,catquanitity;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cartimgview=itemView.findViewById(R.id.cartimgview);
            tvCatName=itemView.findViewById(R.id.tvCatName);
            tvCatPrice=itemView.findViewById(R.id.tvCatPrice);
            //catquanitity=itemView.findViewById(R.id.catquanitity);
            iVRemoveFromCart = itemView.findViewById(R.id.iVRemoveFromCart);

        }
    }
}
