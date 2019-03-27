package com.example.homework1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements Fragment1.FragmentActivity {

    FragmentTransaction fragmentTransaction;
    Fragment2 fragment2;

    @Override
    @SuppressLint("CommitTransaction")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment2 = new Fragment2();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        setContentView(R.layout.activity_main);
    }

    public void setFragmentTransaction(FragmentTransaction fragmentTransaction) {
        this.fragmentTransaction = fragmentTransaction;
    }

    @SuppressLint("CommitTransaction")
    public FragmentTransaction getFragmentTransaction() {
        if (fragment2.isAdded()) return null;
        return getSupportFragmentManager().beginTransaction();
    }

    public void setNumber(Integer number) {
        fragment2.setNumber(number);
    }

    public Fragment2 getFragment2() {
        return fragment2;
    }
}
