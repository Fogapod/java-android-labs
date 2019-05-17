package com.e.lab3_2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                        String[] fullNameParts = fullNameInput.getText().toString().split("\\s+");

                        if (fullNameParts.length != 3) {
                            Toast.makeText(getApplicationContext(), "Invalid number of field", Toast.LENGTH_LONG).show();
                            return;
                        }

                        mDBHelper.putUser(mDB, fullNameParts[0], fullNameParts[1], fullNameParts[2]);

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

        mDBHelper = new DBHelper(this, "students", null, 2);
        mDB = mDBHelper.getWritableDatabase();
    }
}
