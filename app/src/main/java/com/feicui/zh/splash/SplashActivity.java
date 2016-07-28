package com.feicui.zh.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.feicui.zh.R;
import com.feicui.zh.activity.MainActivity;
import com.feicui.zh.common.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnEnter)
    public void enter() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @OnClick(R.id.btnLogin)
    public void login(){

    }
}
