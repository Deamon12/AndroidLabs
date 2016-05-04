package com.example.deamon.myfirstapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentAsyncLab extends Fragment implements View.OnClickListener {

    private ProgressBar progressBar;
    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_async, container, false);


        progressBar = (ProgressBar) rootView.findViewById(R.id.the_progressBar);
        progressBar.setVisibility(View.GONE);

        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(this);

        return rootView;
    }

    private void doFibonacci() {
        progressBar.setVisibility(View.VISIBLE);
        new FibTask().execute("");

    }

    private void setProgressPercent(Integer progress) {
        progressBar.setProgress(progress);
    }


    private void showFibSequence(String sequence) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Fib Sequence...");

        final TextView seqText = new TextView(getActivity());
        seqText.setText(sequence);
        builder.setView(seqText);

        builder.setPositiveButton("Cool", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                progressBar.setVisibility(View.GONE);
            }
        });

        builder.show();


    }


    @Override
    public void onClick(View v) {

        if(v == button){
            doFibonacci();
        }

    }



    private class FibTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {

            String sequence = "0 1";
            long varA = 0;
            long varB = 1;
            long sum = 0;

            int i;
            for (i = 0; i < 1000; i++) {

                sum = varA + varB;
                varA = varB;
                varB = sum;

                sequence = sequence +" "+ sum;

                publishProgress(i);
                if (isCancelled()) break;
            }


            return sequence;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            showFibSequence(result);
            progressBar.setVisibility(View.GONE);
        }
    }




}