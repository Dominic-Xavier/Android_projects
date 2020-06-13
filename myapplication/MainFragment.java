package com.myapp.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainFragment extends AppCompatActivity {

    Button close;
    TabLayout tabs;
    ViewPager swipe;
    TabItem Income,Expense;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);

        close = findViewById(R.id.quit);
        tabs = findViewById(R.id.tabLayout);
        swipe = findViewById(R.id.swiper);
        Income = findViewById(R.id.Income_Tab);
        Expense = findViewById(R.id.Expense_Tab);
        close.setOnClickListener((v)-> {
            startActivity(new Intent(MainFragment.this,Database.class));
            finish();
        });

         PageViewAdapter adapter = new PageViewAdapter(getSupportFragmentManager(),tabs.getTabCount());
        swipe.setAdapter(adapter);

        tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
