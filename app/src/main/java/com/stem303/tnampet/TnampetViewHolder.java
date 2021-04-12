package com.stem303.tnampet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TnampetViewHolder extends RecyclerView.ViewHolder {

    private TextView title;

    public TnampetViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tnampet_title);
    }

    public TextView getTitle(){return title;}
}
