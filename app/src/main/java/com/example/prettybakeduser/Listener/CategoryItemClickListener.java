package com.example.prettybakeduser.Listener;

import android.content.Context;

import com.example.prettybakeduser.Model.Category;

import java.util.ArrayList;

public interface CategoryItemClickListener {

    public void setOnItemClicked(ArrayList<Category> listCategory,int position);


}
