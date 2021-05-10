package com.stem303.tnampet.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stem303.tnampet.R;
import com.stem303.tnampet.ui.recycleView.TnampetItem;

import java.util.ArrayList;

public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        TnampetItem tnampetItem = (TnampetItem) getArguments().getSerializable("tnampetItem");
        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(view.findViewById(R.id.detail_title));
        textViews.add(view.findViewById(R.id.detail_content));
        textViews.add(view.findViewById(R.id.detail_side_effect));
        textViews.add(view.findViewById(R.id.detail_side_effect_content));
        textViews.add(view.findViewById(R.id.detail_warning));
        textViews.add(view.findViewById(R.id.detail_warning_content));
        textViews.add(view.findViewById(R.id.detail_usage));
        textViews.add(view.findViewById(R.id.detail_usage_content));
        textViews.get(0).setText(tnampetItem.getTitle());
        textViews.get(1).setText(tnampetItem.getIntro_content());
        textViews.get(2).setText(tnampetItem.getSide_effect());
        textViews.get(3).setText(tnampetItem.getSide_effect_content());
        textViews.get(4).setText(tnampetItem.getWarning());
        textViews.get(5).setText(tnampetItem.getWarning_content());
        textViews.get(6).setText(tnampetItem.getUsage());
        textViews.get(7).setText(tnampetItem.getUsage_content());

        return  view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu,menu);
    }
}