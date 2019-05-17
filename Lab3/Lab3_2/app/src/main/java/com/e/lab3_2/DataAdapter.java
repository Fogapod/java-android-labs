package com.e.lab3_2;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private Cursor mDataset;

    void fetch()
    {
        mDataset = MainActivity.mDBHelper.getStudents(MainActivity.mDB);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.getCount();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView timestamp, full_name, id;

        MyViewHolder(View v) {
            super(v);

            timestamp = v.findViewById(R.id.timestamp);
            full_name = v.findViewById(R.id.full_name);
            id = v.findViewById(R.id.id);
        }
    }

    @Override
    public DataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mDataset.moveToPosition(position);

        String fullName = mDataset.getString(1) + " "
                + mDataset.getString(2) + " "
                + mDataset.getString(3);

        holder.full_name.setText(fullName);
        holder.timestamp.setText(mDataset.getString(4));
        holder.id.setText(mDataset.getString(0));

        if (position % 2 == 1) {
            holder.itemView.setBackgroundResource(R.color.colorPrimary);
        }

    }

}