package com.example.homework1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment {

    private String number;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        TextView textView = view.findViewById(R.id.textView);
        if (number.charAt(number.length() - 1) % 2 == 0) {
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(Color.BLUE);
        }
        textView.setText(number);
        return view;
    }

    void setNumber(String number) {
        this.number = number;
    }
}
