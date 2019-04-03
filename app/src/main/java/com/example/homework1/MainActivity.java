package com.example.homework1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements Fragment1.FragmentActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int fragment = 0;
        if (savedInstanceState != null) {
            fragment = savedInstanceState.getInt("fragment");
        }

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        if (fragment == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment1)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }

        setContentView(R.layout.activity_main);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment2.isAdded())
            outState.putInt("fragment", 2);
        else
            outState.putInt("fragment", 1);
    }

    public boolean isAddedFragment2() {
        return fragment2.isAdded();
    }

    public void setNumberAndColor(String number, int color) {
        fragment2.setNumberAndColor(number, color);
    }

    public void openFragment2() {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout, fragment2)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
    }
}
