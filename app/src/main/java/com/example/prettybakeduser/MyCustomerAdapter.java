package com.example.prettybakeduser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MyCustomerAdapter extends RecyclerView.Adapter<MyCustomerAdapter.ViewHolder> {

    private CustomerList[] customerLists;

    @NonNull
    @NotNull
    @Override
    public MyCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup view, int viewType) {
        View cardview=LayoutInflater.from(view.getContext()).inflate(R.layout.list_user_row,view,false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyCustomerAdapter.ViewHolder holder, int i) {
        holder.tvName.setText(customerLists[i].getName());
        holder.tvMobNo.setText(customerLists[i].getMobileno());
        holder.tvAddress.setText(customerLists[i].getAddress());
        holder.tvgender.setText(customerLists[i].getGender());

    }

    @Override
    public int getItemCount() {
        return customerLists.length;
    }

    public void setCustomerList(CustomerList[] customerLists) {
        this.customerLists=customerLists;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvMobNo,tvAddress,tvgender;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvMobNo=itemView.findViewById(R.id.tvMobNo);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvgender=itemView.findViewById(R.id.tvGender);


        }
    }
}
