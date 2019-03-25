package com.example.homework1;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Fragment1.FragmentActivity {

    FragmentTransaction fragmentTransaction;
    Fragment2 fragment2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment2 = new Fragment2();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        setContentView(R.layout.activity_main);
    }

    public void setFragmentTransaction(FragmentTransaction fragmentTransaction) {
        this.fragmentTransaction = fragmentTransaction;
    }

    public FragmentTransaction getFragmentTransaction() {
        if (fragment2.isAdded()) return null;
        return getSupportFragmentManager().beginTransaction();
    }

    public void setNumber(String number) {
        fragment2.setNumber(number);
    }

    public Fragment2 getFragment2() {
        return fragment2;
    }
}
