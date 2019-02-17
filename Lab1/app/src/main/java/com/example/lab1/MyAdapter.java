package com.example.lab1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;
    private int color1;
    private int color2;

    public MyAdapter(int c1, int c2) {
        color1 = c1;
        color2 = c2;
    }

    public void setItems(String[] dataset)
    {
        mDataset = dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(String.format("[%s] %s", mDataset[position], numberToString(position + 1)));
        holder.itemView.setBackgroundColor(position % 2 == 0? color1: color2);

    }

    private static final String[] numCodes = {"", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};
    private static final String[] numCodesX10 = {"", " ten", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety"};

    private String numberToString(int number) {
        if (number <= 0) {
            return "zero";
        }

        if (number > 1000000) {
            return "> million";
        }

        if (number == 1000000) {
            return "one million";
        }
        if (number < 1000) {
            return numberToStringUpTo999(number);
        }

        int thousandsMod = number % 1000;
        number /= 1000;

        return numberToStringUpTo999(number) + (number > 1? " thousands": " thousand") + numberToStringUpTo999(thousandsMod);
    }

    private String numberToStringUpTo999(int number) {
        String result;

        if (number % 100 < 20){
            result = numCodes[number % 100];
            number /= 100;
        }
        else {
            result = numCodes[number % 10];
            number /= 10;

            result = numCodesX10[number % 10] + result;
            number /= 10;
        }

        if (number == 0) return result;

        return numCodes[number > numCodes.length? 0: number] + " hundred" + result;
    }
}