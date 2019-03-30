package com.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    final String DATA_URL = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";
    private ViewPager mPager;
    private MyAdapter mAdapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            startActivity(new Intent(this, SplashActivity.class));
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPager = findViewById(R.id.viewPager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        loadData(savedInstanceState == null ? 0 : savedInstanceState.getInt("current_item"));
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("current_item", mPager.getCurrentItem());
    }

    private void loadData(final int savedPage) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                DATA_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                response.remove(0); // get rid of metadata on item 0

                setupUI(response, savedPage);

                sendBroadcast(new Intent("close_splash"));
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                sendBroadcast(new Intent("close_splash"));
                Toast.makeText(getApplicationContext(), "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void setupUI(JSONArray data, int savedPage) {
        editText = findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new MinMaxInputFilter(0, data.length())});
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

        mAdapter.setData(data);
        mPager.setCurrentItem(savedPage, false);
    }

    public void onGoCLick(View view) {
        String text = ((TextView) findViewById(R.id.editText)).getText().toString();
        if (text.equals("")) {
            return;
        }

        int position;

        try {
            position = Integer.parseInt(text) - 1;
        } catch (NumberFormatException nfe) {
            return;
        }

        if (position < 0 || position > mAdapter.getCount()) {
            return;
        }

        mPager.setCurrentItem(
                position, Math.abs(mAdapter.getCurrentPos() - position) < 5);
    }
}
