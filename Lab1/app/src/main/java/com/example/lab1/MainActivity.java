package com.example.lab1;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] data = new String[1000000];
        for (int i = 0; i < data.length; i++) {
            data[i] = String.valueOf(i + 1);
        }
        mAdapter = new MyAdapter(
                ResourcesCompat.getColor(getResources(), R.color.ListViewWhite, null),
                ResourcesCompat.getColor(getResources(), R.color.ListViewGrey, null)
        );
        ((MyAdapter) mAdapter).setItems(data);
        recyclerView.setAdapter(mAdapter);

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
    }

    public void onGoCLick(View view) {
        String text = ((TextView)findViewById(R.id.editText)).getText().toString();
        if (text.equals("")) {
            return;
        }

        int position = Integer.parseInt(text) - 1;

        if (position < 0 || position > mAdapter.getItemCount()) {
            return;
        }

        recyclerView.scrollToPosition(position);
    }
}
