package com.stem303.tnampet.ui.home;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stem303.tnampet.DBHelper;
import com.stem303.tnampet.R;
import com.stem303.tnampet.ui.recycleView.TnampetItem;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private TnampetItem tnampetItem;
    private DBHelper bookmarkDBHelper;
    private MenuItem item;

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
        bookmarkDBHelper = new DBHelper(this.getContext());
        tnampetItem = (TnampetItem) getArguments().getSerializable("tnampetItem");
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
        item = menu.findItem(R.id.action_add_bookmark);
        if(isBookmark()) item.setIcon(R.drawable.ic_marked);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(isBookmark()){
            item.setIcon(R.drawable.ic_menu_bookmark);
            bookmarkDBHelper.onRemove(tnampetItem.getId());
        }else {
            item.setIcon(R.drawable.ic_marked);
            bookmarkDBHelper.onInsert(tnampetItem,DBHelper.BOOKMARK_TABLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isBookmark(){
        Cursor cursor = bookmarkDBHelper.onRetrive(tnampetItem.getId());
        SQLiteDatabase db = bookmarkDBHelper.getReadableDatabase();
        String title;
        try {
            cursor.moveToNext();
            title = cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.ID));
            if(cursor != null && cursor.isClosed()) cursor.close();
            db.close();
            if (!title.isEmpty()) return true;
            else return false;
        } catch (CursorIndexOutOfBoundsException e) {
            if(cursor != null && cursor.isClosed()) cursor.close();
            db.close();
            return false;
        }

    }
}