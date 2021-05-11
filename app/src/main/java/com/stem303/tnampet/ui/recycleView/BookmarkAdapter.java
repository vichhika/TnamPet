package com.stem303.tnampet.ui.recycleView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.stem303.tnampet.MainActivity;
import com.stem303.tnampet.R;
import com.stem303.tnampet.ui.bookmark.BookmarkFragment;
import com.stem303.tnampet.ui.home.DetailFragment;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkViewHolder> {

    private ArrayList<TnampetItem> tnampetItems = new ArrayList<>();
    private BookmarkFragment fragment;

    public BookmarkAdapter(Fragment fragment,ArrayList<TnampetItem> items){
        this.fragment = (BookmarkFragment) fragment;
        this.tnampetItems = items;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.tnampet_item_bookmark;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        TnampetItem currentItem = tnampetItems.get(position);
        holder.getTextView().setText(currentItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                DetailFragment detailFragment = new DetailFragment();
                bundle.putSerializable("tnampetItem",currentItem);
                detailFragment.setArguments(bundle);
                ((MainActivity)fragment.getActivity()).goToFragment(detailFragment);
            }
        });
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.getDB().onRemove(currentItem.getId());
                tnampetItems.remove(currentItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tnampetItems.size();
    }
}
