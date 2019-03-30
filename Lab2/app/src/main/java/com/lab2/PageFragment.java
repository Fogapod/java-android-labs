package com.lab2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class PageFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_GRAPHIC = "arg_graphic";
    static final String ARGUMENT_NAME = "arg_name";
    static final String ARGUMENT_HELP_TEXT = "arg_help_text";
    private final String IMAGE_BASE_URL = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";
    int mPageNumber;

    private String mGraphic;
    private String mName;
    private String mHelpText;

    static PageFragment newInstance(int page, String graphic, String name, String helpText) {
        PageFragment pageFragment = new PageFragment();
        pageFragment.setFields(graphic, name, helpText);

        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_GRAPHIC, graphic);
        arguments.putString(ARGUMENT_NAME, name);
        arguments.putString(ARGUMENT_HELP_TEXT, helpText);

        pageFragment.setArguments(arguments);

        return pageFragment;
    }

    public void setFields(String graphic, String name, String helptext) {
        mGraphic = graphic;
        mName = name;
        mHelpText = helptext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        assert arguments != null;

        mPageNumber = arguments.getInt(ARGUMENT_PAGE_NUMBER);
        mGraphic = arguments.getString(ARGUMENT_GRAPHIC);
        mName = arguments.getString(ARGUMENT_NAME);
        mHelpText = arguments.getString(ARGUMENT_HELP_TEXT);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item, null);

        TextView techName = view.findViewById(R.id.techName);
        techName.setText(mName);
        TextView techDescription = view.findViewById(R.id.techDescription);
        techDescription.setText(mHelpText);

        final ImageView imageView = view.findViewById(R.id.techImageSmall);

        ImageRequest request = new ImageRequest(IMAGE_BASE_URL + mGraphic,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        imageView.setImageResource(R.drawable.ic_launcher_background);
                    }
                });

        VolleyController.getInstance(getContext()).addToRequestQueue(request);

        return view;
    }
}
