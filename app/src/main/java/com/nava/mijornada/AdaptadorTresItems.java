package com.nava.mijornada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorTresItems extends RecyclerView.Adapter<AdaptadorTresItems.MyViewHolder> {
    private Context c;
    private ArrayList<TresItemsView> items;

    public AdaptadorTresItems(ArrayList<TresItemsView> items) {
        this.items = items;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemOne;
        public TextView itemTwo;
        public TextView itemThree;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemOne = itemView.findViewById(R.id.tv1);
            this.itemTwo = itemView.findViewById(R.id.tv2);
            this.itemThree = itemView.findViewById(R.id.tv3);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_three, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TresItemsView actualItem = items.get(position);
        holder.itemOne.setText(actualItem.getTextViewOne());
        holder.itemTwo.setText(actualItem.getTextViewTwo());
        holder.itemThree.setText(actualItem.getTextViewThree());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
