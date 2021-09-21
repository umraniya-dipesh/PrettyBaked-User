package com.example.prettybakeduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prettybakeduser.ApiHelper.WebUrl;
import com.example.prettybakeduser.Listener.CategoryItemClickListener;
import com.example.prettybakeduser.Listener.SubCategoryItemClickListener;
import com.example.prettybakeduser.Model.Category;
import com.example.prettybakeduser.Model.Subcategory;
import com.example.prettybakeduser.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
   Context context;
   ArrayList<Subcategory> listSubCategory;
   SubCategoryItemClickListener subcategoryItemClickListener;
    public SubCategoryItemClickListener getSubcategoryItemClickListener(){
        return subcategoryItemClickListener;
    }

    public void setSubcategoryItemClickListener(SubCategoryItemClickListener subcategoryItemClickListener) {
        this.subcategoryItemClickListener = subcategoryItemClickListener;
    }

    public SubCategoryAdapter(Context context, ArrayList<Subcategory> listSubCategory) {
        this.context = context;
        this.listSubCategory = listSubCategory;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.subcategory_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubCategoryAdapter.ViewHolder holder, int position) {
        Subcategory subcategory=listSubCategory.get(position);
        String name=subcategory.getSub_category_name();
        String price=subcategory.getPrice();
        holder.tvSubCategory.setText(name);
        holder.tvSubCategoryPrice.setText("Price: "+price);
        Glide.with(context).load(WebUrl.KEY_IMAGE_URL1+subcategory.getSub_category_image()).into(holder.ivSubCatimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryItemClickListener listener=getSubcategoryItemClickListener();
                listener.setOnItemClicked(listSubCategory,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSubCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivSubCatimage;
        TextView tvSubCategory,tvSubCategoryPrice;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivSubCatimage=itemView.findViewById(R.id.ivSubCatimage);
            tvSubCategory=itemView.findViewById(R.id.tvSubCategory);
            tvSubCategoryPrice=itemView.findViewById(R.id.tvSubCategoryPrice);
        }
    }
}
