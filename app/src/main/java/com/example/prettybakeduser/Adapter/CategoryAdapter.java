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
import com.example.prettybakeduser.HomeScreenMainActivity;
import com.example.prettybakeduser.Listener.CategoryItemClickListener;
import com.example.prettybakeduser.Model.Category;
import com.example.prettybakeduser.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<Category> listCategory;
    CategoryItemClickListener categoryItemClickListener;

    public CategoryItemClickListener getCategoryItemClickListener() {
        return categoryItemClickListener;
    }

    public void setCategoryItemClickListener(CategoryItemClickListener categoryItemClickListener) {
        this.categoryItemClickListener = categoryItemClickListener;
    }

    public CategoryAdapter(Context context, ArrayList<Category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.category_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.ViewHolder holder, int position) {
        Category category=listCategory.get(position);
        String name=category.getCategory_name();
        holder.tvCategory.setText(name);
        Glide.with(context).load(WebUrl.KEY_IMAGE_URL+category.getCategory_image()).into(holder.ivCatImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryItemClickListener listener=getCategoryItemClickListener();
                listener.setOnItemClicked(listCategory,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCatImage;
        TextView tvCategory;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivCatImage=itemView.findViewById(R.id.ivCatimage);
            tvCategory=itemView.findViewById(R.id.tvCategory);

        }
    }
}
