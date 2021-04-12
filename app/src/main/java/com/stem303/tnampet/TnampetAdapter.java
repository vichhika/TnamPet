package com.stem303.tnampet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TnampetAdapter extends RecyclerView.Adapter<TnampetViewHolder> {

    private ArrayList<TnampetItem> tnampetItems = new ArrayList<>();

    public TnampetAdapter(ArrayList<TnampetItem> items){
        tnampetItems = items;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.tnampet_item;
    }

    @NonNull
    @Override
    public TnampetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new TnampetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TnampetViewHolder holder, int position) {
        TnampetItem currentItem = tnampetItems.get(position);
        holder.getTitle().setText(currentItem.getTitle_tnam());
    }

    @Override
    public int getItemCount() {
        return tnampetItems.size();
    }
}
