package com.example.prettybakeduser.Listener;

import com.example.prettybakeduser.Model.Category;
import com.example.prettybakeduser.Model.Subcategory;

import java.util.ArrayList;

public interface SubCategoryItemClickListener {

    public void setOnItemClicked(ArrayList<Subcategory> listSubCategory, int position);
}
