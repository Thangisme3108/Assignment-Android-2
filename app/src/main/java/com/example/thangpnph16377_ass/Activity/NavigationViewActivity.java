package com.example.thangpnph16377_ass.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.thangpnph16377_ass.Fragment.FragmentGioiThieu;
import com.example.thangpnph16377_ass.Fragment.FragmentQLCongViec;
import com.example.thangpnph16377_ass.R;
import com.google.android.material.navigation.NavigationView;

public class NavigationViewActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setItemIconTintList(null);
        FragmentQLCongViec qlCongViec = new FragmentQLCongViec();
        replaceFragment(qlCongViec);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.qlsp) {
                    replaceFragment(qlCongViec);
                } else if (item.getItemId() == R.id.gioithieu) {
                    FragmentGioiThieu fragmentGioiThieu = new FragmentGioiThieu();
                    replaceFragment(fragmentGioiThieu);
                } else if (item.getItemId() == R.id.dangxuat) {
                    Intent intent = new Intent(NavigationViewActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                }
                drawerLayout.close();
                return true;
            }
        });
    }

    public void replaceFragment(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmNav, frg).commit();
    }
}