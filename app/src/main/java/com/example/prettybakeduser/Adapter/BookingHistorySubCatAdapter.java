package com.example.prettybakeduser.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Model.BookingHistorySubCat;
import com.example.prettybakeduser.Model.Subcategory;
import com.example.prettybakeduser.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookingHistorySubCatAdapter extends RecyclerView.Adapter<BookingHistorySubCatAdapter.ViewHolder>  {
    Context context;
    ArrayList<BookingHistorySubCat> listSubCategory;

    public BookingHistorySubCatAdapter(Context context, ArrayList<BookingHistorySubCat> listSubCategory) {
        this.context = context;
        this.listSubCategory = listSubCategory;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.bookingdetailsubcat_row_file,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookingHistorySubCatAdapter.ViewHolder holder, int position) {
        BookingHistorySubCat bookingHistorySubCat=listSubCategory.get(position);
        String name=bookingHistorySubCat.getSub_category_name();
        String price=bookingHistorySubCat.getPrice();
        Log.d("name",name);
        holder.tvCakeName.setText(name);
        holder.tvamount.setText("₹"+price);
        Glide.with(context).load(WebUrl.KEY_IMAGE_URL1+bookingHistorySubCat.getSub_category_image()).into(holder.ivBookDetaillSubCatimage);

    }

    @Override
    public int getItemCount() {
        return listSubCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivBookDetaillSubCatimage;
        TextView tvCakeName,tvamount;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivBookDetaillSubCatimage=itemView.findViewById(R.id.ivBookDetaillSubCatimage);
            tvCakeName=itemView.findViewById(R.id.tvSubCakeName);
            tvamount=itemView.findViewById(R.id.tvamount);
        }
    }
}
