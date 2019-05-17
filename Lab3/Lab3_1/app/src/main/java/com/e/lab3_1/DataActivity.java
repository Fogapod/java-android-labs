package com.e.lab3_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DataActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        DataAdapter mAdapter = new DataAdapter();


        mAdapter.fetch();

        recyclerView.setAdapter(mAdapter);
    }
}
