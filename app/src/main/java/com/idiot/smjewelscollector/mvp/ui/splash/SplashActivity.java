package com.idiot.smjewelscollector.mvp.ui.splash;

import android.os.Bundle;

import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.ActivitySplashBinding;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    SplashContract.Presenter mPresenter;
    ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
        init();
        mBinding.getRoot();
    }


    private void init() {
        setupNavigator();
        NavigationUtil.INSTANCE.setSplash();
    }

    protected void setupNavigator() {
        NavigationUtil.INSTANCE.setupNavigator(this, getSupportActionBar(), getmBinding(), null);
    }

    public ActivitySplashBinding getmBinding(){
        return (ActivitySplashBinding) mBinding;
    }



    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}