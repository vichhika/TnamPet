package com.stem303.tnampet.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
        tnampetAdapter = new TnampetAdapter(this,this.tnampetItems);
        mRecyclerView = view.findViewById(R.id.recyclerview_home);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(tnampetAdapter);
        jsonParse();
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

    private void jsonParse(){
        final ProgressDialog progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        tnampetItems.clear();
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        tnampetItems.add(new TnampetItem(
                                jsonObject.getInt("id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("intro_content"),
                                jsonObject.getString("side_effect"),
                                jsonObject.getString("side_effect_content"),
                                jsonObject.getString("warning"),
                                jsonObject.getString("warning_content"),
                                jsonObject.getString("usage"),
                                jsonObject.getString("usage_content")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
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