package com.stem303.tnampet.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stem303.tnampet.DBHelper;
import com.stem303.tnampet.R;
import com.stem303.tnampet.ui.recycleView.TnampetAdapter;
import com.stem303.tnampet.ui.recycleView.TnampetItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private ArrayList<TnampetItem> tnampetItems = new ArrayList<>();
    private TnampetAdapter tnampetAdapter;
    private RecyclerView mRecyclerView;
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private String url = "https://medicine-303.herokuapp.com/medicines";
    private DBHelper homeDBHelper;
    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        homeDBHelper = new DBHelper(this.getContext());
        tnampetAdapter = new TnampetAdapter(this,this.tnampetItems);
        mRecyclerView = view.findViewById(R.id.recyclerview_home);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(tnampetAdapter);
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

    public void fetchData(){
        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        tnampetItems.clear();
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            boolean status;

            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        TnampetItem tnampetItem = new TnampetItem(
                                jsonObject.getInt("id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("intro_content"),
                                jsonObject.getString("side_effect"),
                                jsonObject.getString("side_effect_content"),
                                jsonObject.getString("warning"),
                                jsonObject.getString("warning_content"),
                                jsonObject.getString("usage"),
                                jsonObject.getString("usage_content")
                        );
                        tnampetItems.add(tnampetItem);
                        homeDBHelper.onInsert(tnampetItem,homeDBHelper.HOME_TABLE);
                        status = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status = false;
                        progressDialog.dismiss();
                    }
                    Collections.sort(tnampetItems, new Comparator<TnampetItem>() {
                        @Override
                        public int compare(TnampetItem tnampetItem, TnampetItem t1) {
                            return tnampetItem.getTitle().compareTo(t1.getTitle());
                        }
                    });
                    tnampetAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Cursor cursor = homeDBHelper.onRetriveAll(homeDBHelper.HOME_TABLE);
                SQLiteDatabase db = homeDBHelper.getReadableDatabase();
                try {
                    while (cursor.moveToNext()){
                        tnampetItems.add(new TnampetItem(
                                cursor.getInt(cursor.getColumnIndex(homeDBHelper.ID)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.title)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.intro_content)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.side_effect)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.side_effect_content)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.warning)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.warning_content)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.usage)),
                                cursor.getString(cursor.getColumnIndex(homeDBHelper.usage_content))
                        ));
                    }
                }finally {
                    if(cursor != null && cursor.isClosed()) cursor.close();
                    db.close();
                }
                Collections.sort(tnampetItems, new Comparator<TnampetItem>() {
                    @Override
                    public int compare(TnampetItem tnampetItem, TnampetItem t1) {
                        return tnampetItem.getTitle().compareTo(t1.getTitle());
                    }
                });
                tnampetAdapter.notifyDataSetChanged();
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });
        requestQueue = Volley.newRequestQueue(this.getContext());
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    public Filter getFilter(){
        return tnampetAdapter.getFilter();
    }

}