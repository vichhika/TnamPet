package com.stem303.tnampet.ui.recycleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.stem303.tnampet.MainActivity;
import com.stem303.tnampet.R;
import com.stem303.tnampet.ui.bookmark.BookmarkFragment;
import com.stem303.tnampet.ui.home.DetailFragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TnampetAdapter extends RecyclerView.Adapter<TnampetViewHolder> implements Filterable {

    private ArrayList<TnampetItem> tnampetItems = new ArrayList<>();
    private ArrayList<TnampetItem> tnampetItems_backup = new ArrayList<>();
    private Fragment c;

    public TnampetAdapter(Fragment c,ArrayList<TnampetItem> items){
        this.c = c;
        this.tnampetItems = items;
        this.tnampetItems_backup = items;
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
    public void onBindViewHolder(@NonNull TnampetViewHolder holder, int position){
        TnampetItem currentItem = tnampetItems.get(position);
        holder.getTitle().setText(currentItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Bundle bundle = new Bundle();
                DetailFragment detailFragment = new DetailFragment();
                bundle.putSerializable("tnampetItem",currentItem);
                detailFragment.setArguments(bundle);
                ((MainActivity)c.getActivity()).goToFragment(detailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tnampetItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<TnampetItem> filterTnam = new ArrayList<>();
                List<TnampetItem> tmpItems = new ArrayList<>(tnampetItems_backup);
                if(charSequence.toString().isEmpty()){
                    filterTnam.addAll(tnampetItems_backup);
                }else{
                    for(TnampetItem item: tmpItems){
                        if(item.getTitle().toLowerCase().contains(charSequence.toString().toString())){
                            filterTnam.add(item);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterTnam;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tnampetItems = (ArrayList<TnampetItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
