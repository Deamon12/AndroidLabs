package com.example.deamon.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetLabActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private JSONArray resultsArray;

    private Button searchButton;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netlab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
        editText.setOnClickListener(this);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (!Singleton.hasBeenInitialized()) {
            Singleton.initialize(this);
        }

        doSearch("");



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



    private void doSearch(String query){

        String url;
        if(query.equalsIgnoreCase("")){
            url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=android";
        }else{
            url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q="+query;
        }


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






    @Override
    public void onClick(View v) {
        if(v == searchButton){
            doSearch(editText.getText().toString());
        }

    }

    @Override
    public void onResponse(JSONObject response) {

        try {

            resultsArray = response.getJSONObject("responseData").getJSONArray("results");

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

            try {

                viewHolder.titleText.setText(dataSet.getJSONObject(position).getString("titleNoFormatting"));
                viewHolder.urlText.setText(Html.fromHtml(dataSet.getJSONObject(position).getString("url")));
                viewHolder.description.setText(Html.fromHtml(dataSet.getJSONObject(position).getString("content")));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return dataSet.length();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView titleText;
            public TextView urlText;
            public TextView description;

            //Declare views here, dont fill them
            public ViewHolder(View itemView) {
                super(itemView);

                titleText = (TextView) itemView.findViewById(R.id.the_title);
                urlText = (TextView) itemView.findViewById(R.id.the_url);
                description = (TextView) itemView.findViewById(R.id.the_description);

            }



        }


    }






}

