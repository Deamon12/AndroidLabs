package com.example.deamon.myfirstapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<People> peopleNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        readDatabase();



    }


    private void readDatabase() {

        SQLiteDatabase mydatabase = openOrCreateDatabase("PeopleDB", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS People (Id INTEGER PRIMARY KEY, Name VARCHAR(32))");

        Cursor dbResult = mydatabase.rawQuery("SELECT * FROM People", null);


        if (dbResult.getCount() > 0) {

            dbResult.moveToFirst();
            peopleNames = new ArrayList<>();

            for (int a = 0; a < dbResult.getCount(); a++) {

                peopleNames.add(new People(dbResult.getInt(0), dbResult.getString(1)));
                dbResult.moveToNext();
            }

            populateRecyclerView();
        }

        mydatabase.close();

    }

    public void removeFromDatabase(int idToRemove) {

        SQLiteDatabase mydatabase = openOrCreateDatabase("PeopleDB", MODE_PRIVATE, null);
        mydatabase.execSQL("DELETE FROM People WHERE Id = '" + idToRemove + "'");
        mydatabase.close();

    }

    public void updateDatabase(int idToEdit, String nameToEdit) {

        SQLiteDatabase mydatabase = openOrCreateDatabase("PeopleDB", MODE_PRIVATE, null);
        mydatabase.execSQL("UPDATE People SET name = '"+nameToEdit+"' WHERE Id = '" + idToEdit + "'");
        mydatabase.close();
        readDatabase();

    }



    private void populateRecyclerView() {

        mAdapter = new RecycleViewAdapter(R.layout.card_layout, peopleNames);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void createAndAddPerson() {

        SQLiteDatabase mydatabase = openOrCreateDatabase("PeopleDB", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS People(Id INTEGER PRIMARY KEY, Name VARCHAR(32))");

        int gender = new Random().nextInt(2);

        if (gender == 1) {
            mydatabase.execSQL("INSERT INTO People VALUES('" + new Random().nextInt() + "','" + getBoyName() + "');");
        } else {
            mydatabase.execSQL("INSERT INTO People VALUES('" + new Random().nextInt() + "','" + getGirlName() + "');");
        }

        mydatabase.close();


    }

    private String getBoyName() {

        String[] names = {
                "Hoyt",
                "Darrin",
                "Bret",
                "Harland",
                "Cletus",
                "Galen",
                "Shaun",
                "Terrance",
                "Gregg",
                "Angelo",
                "Lacy",
                "Zachariah",
                "Demetrius",
                "Renaldo"};

        int index = new Random().nextInt(names.length);

        return names[index];
    }

    private String getGirlName() {

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

            createAndAddPerson();
            readDatabase();
            mAdapter.notifyDataSetChanged();
        }
    }




    private void showClickedDialog(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What to do...");

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                showEditDialog(index);

            }
        });
        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                removeFromDatabase(peopleNames.get(index).getId());
                readDatabase();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.show();
    }


    private void showEditDialog(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update person name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input.setText(peopleNames.get(index).getName());
        builder.setView(input);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateDatabase(peopleNames.get(index).getId(), input.getText().toString());

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.show();

    }


    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private int rowLayout;
        private ArrayList<People> dataSet;

        public RecycleViewAdapter(int rowLayout, ArrayList dataSet) {
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

            viewHolder.nameView.setText(dataSet.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

            public TextView nameView;

            //Declare views here, dont fill them
            public ViewHolder(View itemView) {
                super(itemView);

                nameView = (TextView) itemView.findViewById(R.id.the_textview);

                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            @Override
            public void onClick(View v) {
                System.out.println("Clicked: " + dataSet.get(getAdapterPosition()).getId());

                showClickedDialog(getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {

                removeFromDatabase(dataSet.get(getAdapterPosition()).getId());
                dataSet.remove(getAdapterPosition());
                mAdapter.notifyDataSetChanged();

                return false;
            }
        }


    }




}


