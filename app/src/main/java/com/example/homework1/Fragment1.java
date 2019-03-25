package com.example.homework1;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

public class Fragment1 extends Fragment {

    private int count = 100;
    private RecyclerView recyclerView;
    private FragmentActivity fragmentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        final ArrayList<ArrayList<String>>  portrait = new ArrayList<>(), landscape = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i % 3 == 0) portrait.add(new ArrayList<String>());
            if (i % 4 == 0) landscape.add(new ArrayList<String>());
            portrait.get(i / 3).add(String.valueOf(i + 1));
            landscape.get(i / 4).add(String.valueOf(i + 1));
        }

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count % 3 == 0) portrait.add(new ArrayList<String>());
                if (count % 4 == 0) landscape.add(new ArrayList<String>());
                portrait.get(count / 3).add(String.valueOf(count + 1));
                landscape.get(count / 4).add(String.valueOf(count + 1));
                if (Fragment1.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(count / 3);
                } else {
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(count / 4);
                }
                count++;
            }
        });

        recyclerView = view.findViewById(R.id.list);
        final LinearLayoutManager layout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layout);
        if (Fragment1.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setAdapter(new MyAdapter(portrait));
        } else {
            recyclerView.setAdapter(new MyAdapter(landscape));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ArrayList<TextView> mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = new ArrayList<>(3);
            mTextView.add((TextView) itemView.findViewById(R.id.textElem1));
            mTextView.add((TextView) itemView.findViewById(R.id.textElem2));
            mTextView.add((TextView) itemView.findViewById(R.id.textElem3));
            if (Fragment1.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mTextView.add((TextView) itemView.findViewById(R.id.textElem4));
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<ArrayList<String>> mData;

        MyAdapter(List<ArrayList<String>> data) {
            mData = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v;
            if (Fragment1.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                v = inflater.inflate(R.layout.list_element_landscape, viewGroup, false);
            } else {
                v = inflater.inflate(R.layout.list_element_portrait, viewGroup, false);
            }
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            int j = 0;
            List<String> str = mData.get(i);
            while (j < str.size()) {
                final String s = str.get(j);
                if (s.charAt(s.length() - 1) % 2 == 0) {
                    myViewHolder.mTextView.get(j).setTextColor(Color.RED);
                } else {
                    myViewHolder.mTextView.get(j).setTextColor(Color.BLUE);
                }
                myViewHolder.mTextView.get(j).setText(s);
                myViewHolder.mTextView.get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction fragmentTransaction = fragmentActivity.getFragmentTransaction();
                        if (fragmentTransaction != null) {
                            Fragment2 fragment2 = fragmentActivity.getFragment2();
                            fragmentActivity.setNumber(s);
                            fragmentTransaction.add(R.id.fragment1, fragment2).addToBackStack(null).commitAllowingStateLoss();
                            fragmentActivity.setFragmentTransaction(fragmentTransaction);
                        }
                    }
                });
                j++;
            }
            while (j < myViewHolder.mTextView.size()) {
                myViewHolder.mTextView.get(j).setText("");
                myViewHolder.mTextView.get(j).setOnClickListener(null);
                j++;
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentActivity = (FragmentActivity) activity;
    }

    public interface FragmentActivity {
        void setFragmentTransaction(FragmentTransaction setFragmentTransaction);

        FragmentTransaction getFragmentTransaction();

        void setNumber(String number);

        Fragment2 getFragment2();
    }
}
