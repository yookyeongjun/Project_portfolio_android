package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    MainTab1 tab1;
    MainTab2 tab2;
    MainTab3 mainTab3;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        android.util.Log.i("activity", "onCreate()");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContentsInput.class);
                startActivity(intent);
            }
        });

        tab1 = new MainTab1();
        tab2 = new MainTab2();
        mainTab3 = new MainTab3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, tab1).commit();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("전체보기"));
        tabLayout.addTab(tabLayout.newTab().setText("냉장"));
        tabLayout.addTab(tabLayout.newTab().setText("냉동"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment frselected = null;
                if (position == 0) {
                    frselected = tab1;
                } else if (position == 1) {
                    frselected = tab2;
                } else if (position == 2) {
                    frselected = mainTab3;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, frselected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }




    // 메뉴바
    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 메뉴바 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_btn1:
                // Toast.makeText(this, "카트", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), CartView.class);
                startActivity(intent2);
                return true;
            case R.id.action_btn2:
                // Toast.makeText(this, "카트", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(), RecipeView.class);
                startActivity(intent3);
                return true;

            case R.id.action_btn3:
                // Toast.makeText(this, "장바구니", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(getApplicationContext(), UseListView.class);
                startActivity(intent4);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}