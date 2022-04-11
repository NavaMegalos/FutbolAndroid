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

public class AdaptadorTresItems extends RecyclerView.Adapter<AdaptadorTresItems.MyViewHolder>
    implements View.OnClickListener{

    private ArrayList<TresItemsView> items;
    private View.OnClickListener listener;
    private boolean colorActivado = true;


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
        if(this.colorActivado) {
            v.setBackgroundColor(Color.parseColor("#cfd8dc"));
            this.colorActivado = false;
        }else {
            v.setBackgroundColor(Color.parseColor("#eeeeee"));
            this.colorActivado = true;
        }
        v.setOnClickListener(this);
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

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (this.listener != null) {
            listener.onClick(view);
        }

    }

}
