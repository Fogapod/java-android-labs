package com.e.lab3_1;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase mDB;
    static DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button viewBtn = findViewById(R.id.button_view);
        Button addBtn = findViewById(R.id.button_add);
        Button updateBtn = findViewById(R.id.button_update);

        viewBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openIntent = new Intent(getApplicationContext(), DataActivity.class);
                        getApplicationContext().startActivity(openIntent);
                    }
                }
        );

        addBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText fullNameInput = findViewById(R.id.full_name_input);

                        mDBHelper.putUser(mDB, fullNameInput.getText().toString());

                        fullNameInput.setText("");
                    }
                }
        );

        updateBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDBHelper.updateLast(mDB);
                    }
                }
        );

        mDBHelper = new DBHelper(this, "students", null, 1);
        mDB = mDBHelper.getWritableDatabase();
    }
}
