package com.example.homework1;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

public class Fragment1 extends Fragment implements ItemClickListener {

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
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        final ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            data.add(String.valueOf(i));
        }

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.add(String.valueOf(++count));
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(count);
            }
        });

        recyclerView = view.findViewById(R.id.list);
        int numberOfColumns = Fragment1.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        MyAdapter adapter = new MyAdapter(data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> mData;
        private ItemClickListener mClickListener;

        MyAdapter(List<String> data) {
            this.mData = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.list_element, viewGroup, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            String data = mData.get(i);
            myViewHolder.mTextView.setText(data);
            if (data.charAt(data.length() - 1) % 2 == 0) {
                myViewHolder.mTextView.setTextColor(Color.RED);
            } else {
                myViewHolder.mTextView.setTextColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView mTextView;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.textElem);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            }
        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentActivity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivity = null;
    }

    @Override
    public void onItemClick(View view, int i) {
        FragmentTransaction fragmentTransaction = fragmentActivity.getFragmentTransaction();
        if (fragmentTransaction != null) {
            Fragment2 fragment2 = fragmentActivity.getFragment2();
            fragmentActivity.setNumber(i + 1);
            fragmentTransaction.add(R.id.fragment1, fragment2).addToBackStack(null).commitAllowingStateLoss();
            fragmentActivity.setFragmentTransaction(fragmentTransaction);
        }
    }

    public interface FragmentActivity {
        void setFragmentTransaction(FragmentTransaction setFragmentTransaction);

        FragmentTransaction getFragmentTransaction();

        void setNumber(Integer number);

        Fragment2 getFragment2();
    }
}
