package com.example.prettybakeduser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prettybakeduser.HomeScreenMainActivity;
import com.example.prettybakeduser.Model.Subcategory;
import com.example.prettybakeduser.Model.city;
import com.example.prettybakeduser.R;
import com.example.prettybakeduser.SubcategoryActivity;

public class Mycityadapter extends RecyclerView.Adapter<Mycityadapter.ViewHolder>{
private city[] citylist;
        Context context;

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_city_row, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvcity.setText(citylist[position].getCity());
        holder.imgv.setImageResource(citylist[position].getImg());

        holder.ll.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Toast.makeText(context,citylist[position].getCity(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context, HomeScreenMainActivity.class);
        context.startActivity(intent);
        }
        });
}

@Override
public int getItemCount() {
        return citylist.length; }

public void setItems(city[] cityli) {
        this.citylist=cityli; }

public static class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvcity;
    private final ImageView imgv;
    private final CardView ll;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        tvcity=itemView.findViewById(R.id.city);
        imgv=itemView.findViewById(R.id.citylogo);
        ll=itemView.findViewById(R.id.customlinear);


    }
}
}
