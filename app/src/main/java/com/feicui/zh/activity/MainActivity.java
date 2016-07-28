package com.feicui.zh.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.feicui.zh.R;
import com.feicui.zh.fagment.HotFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.navigationView) NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;
    HotFragment hotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**抽屉内容监听*/
        navigationView.setNavigationItemSelectedListener(listener);

        setSupportActionBar(toolbar);
        /**给左图标加上返回*/
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        /**图标动画改变*/
        drawerLayout.addDrawerListener(drawerToggle);
        /**同步*/
        drawerToggle.syncState();

        hotFragment = new HotFragment();
        replaceFragment(hotFragment);
    }
    /**碎片替换*/
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    private NavigationView.OnNavigationItemSelectedListener listener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.github_hot_repo:
                    Toast.makeText(MainActivity.this, "AA", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.github_hot_coder:
                    Toast.makeText(MainActivity.this, "BB", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.github_trend:
                    Toast.makeText(MainActivity.this, "CC", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };
}
