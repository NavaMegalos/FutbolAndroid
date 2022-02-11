package com.nava.mijornada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorDosItems extends RecyclerView.Adapter<AdaptadorDosItems.ViewHolderTwoItems> {
    private Context c;
    private ArrayList<DosItemsView> items;

    public AdaptadorDosItems(ArrayList<DosItemsView> items) {
        this.items = items;
    }

    public static class ViewHolderTwoItems extends RecyclerView.ViewHolder {
        public TextView itemOne;
        public TextView itemTwo;

        public ViewHolderTwoItems(@NonNull View itemView) {
            super(itemView);
            this.itemOne = itemView.findViewById(R.id.tv1);
            this.itemTwo = itemView.findViewById(R.id.tv2);
        }

    }

    @NonNull
    @Override
    public ViewHolderTwoItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_two, parent, false);
        return new ViewHolderTwoItems(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTwoItems holder, int position) {
        DosItemsView actualItem = items.get(position);
        holder.itemOne.setText(actualItem.getTextViewOne());
        holder.itemTwo.setText(actualItem.getTextViewTwo());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
