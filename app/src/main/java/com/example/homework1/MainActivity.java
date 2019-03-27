package com.example.homework1;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements Fragment1.FragmentActivity {

    Fragment2 fragment2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment2 = new Fragment2();

        setContentView(R.layout.activity_main);
    }

    public boolean isAdded() {
        return fragment2.isAdded();
    }

    public void setNumber(Integer number) {
        fragment2.setNumber(number);
    }

    public void openFragment2() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment1, fragment2)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
