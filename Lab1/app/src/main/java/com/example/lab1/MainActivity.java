package com.example.lab1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new OnLoading().execute();
    }

    public void onGoCLick(View view) {
        String text = ((TextView)findViewById(R.id.editText)).getText().toString();
        if (text.equals("")) {
            return;
        }

        int position;

        try {
            position = Integer.parseInt(text) - 1;
        } catch (NumberFormatException nfe) {
            return;
        }

        if (position < 0 || position > mAdapter.getItemCount()) {
            return;
        }

        recyclerView.scrollToPosition(position);
    }

    class OnLoading extends AsyncTask<Void,Void,Void> {

        private ConstraintLayout progressLayout;
        private ProgressBar pb;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressLayout = MainActivity.this.findViewById(R.id.progressLayout);
            progressLayout.setVisibility(ConstraintLayout.VISIBLE);

            pb = MainActivity.this.findViewById(R.id.progressBar);
            pb.setMax(100);
            pb.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            recyclerView = findViewById(R.id.recycler_view);

            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new MyAdapter(
                    ResourcesCompat.getColor(getResources(), R.color.ListViewWhite, null),
                    ResourcesCompat.getColor(getResources(), R.color.ListViewGrey, null)
            );
            recyclerView.setAdapter(mAdapter);

            pb.setProgress(10);

            final String[] data = new String[1000000];

            int progressStepSize = data.length / 8;
            int nextProgressValue = 0;

            for (int i = 0; i < data.length; i++) {
                if (i == nextProgressValue) {
                    pb.setProgress(pb.getProgress() + 8);
                    nextProgressValue += progressStepSize;
                }
                data[i] = String.valueOf(i + 1);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((MyAdapter) mAdapter).setItems(data);
                }
            });

            editText = findViewById(R.id.editText);
            editText.setFilters(new InputFilter[]{new MinMaxInputFilter(0, data.length)});
            editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                        onGoCLick(editText);

                        return true;
                    }

                    return false;
                }
            });

            pb.setProgress(pb.getMax());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressLayout.setVisibility(ConstraintLayout.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
