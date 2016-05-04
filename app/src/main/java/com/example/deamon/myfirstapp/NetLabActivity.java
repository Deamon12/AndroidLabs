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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NetLabActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {

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

        String serverId = "012448826571339543623%3A3s8s8fcybio";
        String apiKey = "AIzaSyBq5aJ7NBlizuKCmt0BUp7ITBjg0HVIELw";


        //OLD - https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=android
        //WORKS - https://www.googleapis.com/customsearch/v1?q=butt&cx=012448826571339543623%3A3s8s8fcybio&key=AIzaSyBq5aJ7NBlizuKCmt0BUp7ITBjg0HVIELw

        String url;
        if(query.equalsIgnoreCase("")){
            url = "https://www.googleapis.com/customsearch/v1?q=android&cx="+serverId+"&key="+apiKey;
        }else{
            try {
                query = URLEncoder.encode(query, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url = "https://www.googleapis.com/customsearch/v1?q="+query+"&cx="+serverId+"&key="+apiKey;
        }


        System.out.println("url: "+url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url, (JSONObject) null, this, this);


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

            System.out.println("resultsArray: "+response.getJSONArray("items"));
            resultsArray = response.getJSONArray("items");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(resultsArray != null && resultsArray.length() > 0)
            populateRecyclerView();


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("error: "+error);
        Toast.makeText(this, "Error searching for "+editText.getText(), Toast.LENGTH_LONG).show();
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


                viewHolder.titleText.setText(dataSet.getJSONObject(position).getString("title"));
                viewHolder.urlText.setText(Html.fromHtml(dataSet.getJSONObject(position).getString("link")));
                viewHolder.description.setText(Html.fromHtml(dataSet.getJSONObject(position).getString("htmlSnippet")));

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

