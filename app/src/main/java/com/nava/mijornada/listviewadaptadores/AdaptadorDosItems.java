package com.nava.mijornada.listviewadaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nava.mijornada.R;

import java.util.ArrayList;

public class AdaptadorDosItems extends RecyclerView.Adapter<AdaptadorDosItems.ViewHolderTwoItems>
    implements  View.OnClickListener{
    private ArrayList<DosItemsView> items;
    private View.OnClickListener listener;
    private boolean colorActivado = true;

    public AdaptadorDosItems(ArrayList<DosItemsView> items) {
        this.items = items;
    }

    @Override
    public void onClick(View view) {
        if(this.listener != null)
            this.listener.onClick(view);
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
        if(this.colorActivado) {
            v.setBackgroundColor(Color.parseColor("#cfd8dc"));
            this.colorActivado = false;
        }else {
            v.setBackgroundColor(Color.parseColor("#eeeeee"));
            this.colorActivado = true;
        }
        v.setOnClickListener(this);
        return new ViewHolderTwoItems(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
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
