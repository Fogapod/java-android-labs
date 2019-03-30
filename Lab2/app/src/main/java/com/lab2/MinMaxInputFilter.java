package com.lab2;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxInputFilter implements InputFilter {
    private int min, max;

    MinMaxInputFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int value;

        try {
            value = Integer.parseInt(dest.toString() + source.toString());
            if (min <= value && value <= max) {
                return null;
            }
        } catch (NumberFormatException ignored) {
        }

        return "";
    }

}
