package com.stem303.tnampet.ui.recycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stem303.tnampet.R;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private ImageView imageView;

    public BookmarkViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tnampet_bookmark_title);
        imageView = itemView.findViewById(R.id.tnampet_bookmark_clear);
    }

    public TextView getTextView(){
        return textView;
    }

    public ImageView getImageView(){
        return  imageView;
    }
}
