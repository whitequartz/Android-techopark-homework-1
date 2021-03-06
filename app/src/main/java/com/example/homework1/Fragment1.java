package com.example.homework1;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
    final private ArrayList<String> data = new ArrayList<>();

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

    public static int getColor(String number) {
        if (number.charAt(number.length() - 1) % 2 == 0) {
            return Color.RED;
        }
        return Color.BLUE;
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
            String number = mData.get(i);
            myViewHolder.mTextView.setText(number);
            myViewHolder.mTextView.setTextColor(getColor(number));
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
        if (!fragmentActivity.isAddedFragment2()) {
            fragmentActivity.setNumberAndColor(data.get(i), getColor(data.get(i)));
            fragmentActivity.openFragment2();
        }
    }

    public interface FragmentActivity {
        boolean isAddedFragment2();

        void setNumberAndColor(String number, int color);

        void openFragment2();
    }
}
