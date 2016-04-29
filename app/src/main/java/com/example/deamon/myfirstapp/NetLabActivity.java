package com.example.deamon.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class NetLabActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private JSONArray resultsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netlab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        if (!Singleton.hasBeenInitialized()) {
            Singleton.initialize(this);
        }

        doSearch();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_my, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_styling:
                Intent tableViewActivity = new Intent(NetLabActivity.this, StylingActivity.class);
                startActivity(tableViewActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void doSearch(){

        String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=dogs";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                (JSONObject) null, this, this);

        // Access the RequestQueue through your singleton class.
        Singleton.getInstance().addToRequestQueue(jsObjRequest);
    }




    private void populateRecyclerView() {

        mAdapter = new RecycleViewAdapter(R.layout.card_layout_netlab, resultsArray);
        mRecyclerView.setAdapter(mAdapter);

    }




    private String getTopic() {

        String[] names = {
                "Corene",
                "Lyn",
                "Zulma",
                "Cicely",
                "Tonie",
                "Marcia",
                "Shawanna",
                "Lavonna",
                "Kiesha",
                "Maud",
                "Karma",
                "Rosenda",
                "Magdalena",
                "Mariela",
                "Luz",
                "Margret",
                "Camellia"};

        int index = new Random().nextInt(names.length);

        return names[index];
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {

            doSearch();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println("Response: "+response);

        try {
            System.out.println("Response: "+response.getJSONObject("responseData").getJSONArray("results"));

            resultsArray = response.getJSONObject("responseData").getJSONArray("results");



            for(int a = 0; a < resultsArray.length(); a++){
                System.out.println("obj "+a+ ": "+resultsArray.getJSONObject(a));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        populateRecyclerView();


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private int rowLayout;
        private JSONArray dataSet;

        public RecycleViewAdapter(int rowLayout, JSONArray dataSet) {
            this.rowLayout = rowLayout;
            this.dataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            //viewHolder.nameView.setText(dataSet.get(position).getName());
            //viewHolder.idView.setText((dataSet.get(position).getId()+""));

        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView nameView;
            public TextView idView;

            //Declare views here, dont fill them
            public ViewHolder(View itemView) {
                super(itemView);

                nameView = (TextView) itemView.findViewById(R.id.the_name);
                idView = (TextView) itemView.findViewById(R.id.the_id);


            }



        }


    }






}

