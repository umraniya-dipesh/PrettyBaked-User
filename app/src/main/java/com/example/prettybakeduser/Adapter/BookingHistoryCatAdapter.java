package com.example.prettybakeduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prettybakeduser.Listener.BookingDetailsCatClickListener;
import com.example.prettybakeduser.Model.BookingHistoryCat;
import com.example.prettybakeduser.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookingHistoryCatAdapter extends RecyclerView.Adapter<BookingHistoryCatAdapter.ViewHolder> {

    Context context;
    ArrayList<BookingHistoryCat> listbookinghistory;
    BookingDetailsCatClickListener bookingDetailsCatClickListener;

    public BookingDetailsCatClickListener getBookingDetailsCatClickListener() {
        return bookingDetailsCatClickListener;
    }

    public void setBookingDetailsCatClickListener(BookingDetailsCatClickListener bookingDetailsCatClickListener) {
        this.bookingDetailsCatClickListener = bookingDetailsCatClickListener;
    }

    public BookingHistoryCatAdapter(Context context, ArrayList<BookingHistoryCat> listbookinghistory) {
        this.context = context;
        this.listbookinghistory = listbookinghistory;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.booking_history_row_file,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookingHistoryCatAdapter.ViewHolder holder, int position) {
        BookingHistoryCat bookingHistoryCat=listbookinghistory.get(position);
        String amount=bookingHistoryCat.getTotal_amount();
        String items=bookingHistoryCat.getTotal_items();
        String cartid=bookingHistoryCat.getCart_id();

        holder.tvitems.setText(items);
        holder.tvtotalamount.setText(amount);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingDetailsCatClickListener listener=getBookingDetailsCatClickListener();
                listener.setOnItemClicked(listbookinghistory,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listbookinghistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtotalamount,tvitems;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvtotalamount=itemView.findViewById(R.id.tvtotalamount);
            tvitems=itemView.findViewById(R.id.tvitems);
        }
    }
}
