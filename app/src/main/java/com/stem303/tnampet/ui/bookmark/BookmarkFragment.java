package com.stem303.tnampet.ui.bookmark;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.stem303.tnampet.DBHelper;
import com.stem303.tnampet.R;
import com.stem303.tnampet.ui.recycleView.BookmarkAdapter;
import com.stem303.tnampet.ui.recycleView.TnampetItem;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {

    private DBHelper bookmarkDBHelper;
    private ArrayList<TnampetItem> tnampetItems = new ArrayList<>();
    private BookmarkAdapter bookmarkAdapter;
    private RecyclerView recyclerView;

    public BookmarkFragment() {
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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmarkDBHelper = new DBHelper(this.getContext());
        bookmarkAdapter = new BookmarkAdapter(this,tnampetItems);
        recyclerView = view.findViewById(R.id.recyclerview_bookmark);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(bookmarkAdapter);
        retriveData();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bookmark_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        bookmarkDBHelper.onClear();
        retriveData();
        return super.onOptionsItemSelected(item);
    }

    public DBHelper getDB(){
        return bookmarkDBHelper;
    }

    private void retriveData(){
        tnampetItems.clear();
        Cursor cursor = bookmarkDBHelper.onRetriveAll(DBHelper.BOOKMARK_TABLE);
        SQLiteDatabase db = bookmarkDBHelper.getReadableDatabase();
        try {
            while (cursor.moveToNext()){
                tnampetItems.add(new TnampetItem(
                        cursor.getInt(cursor.getColumnIndex(bookmarkDBHelper.ID)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.title)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.intro_content)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.side_effect)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.side_effect_content)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.warning)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.warning_content)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.usage)),
                        cursor.getString(cursor.getColumnIndex(bookmarkDBHelper.usage_content))
                        ));
            }
        }finally {
            if(cursor != null && cursor.isClosed()) cursor.close();
            db.close();
        }
        bookmarkAdapter.notifyDataSetChanged();
    }
}