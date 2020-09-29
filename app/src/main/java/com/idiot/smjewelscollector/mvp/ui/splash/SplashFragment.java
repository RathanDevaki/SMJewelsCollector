package com.idiot.smjewelscollector.mvp.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentSplashBinding;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtil;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment implements SplashContract.View {

    FragmentSplashBinding mBinding;
    SplashPresenter mPresenter;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash,container,false);
        mPresenter = new SplashPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPresenter.checkInternet(getContext())){// Checking of internet connection

                    if (mPresenter.checkLogin()) { //Already user is logged in
                        NavigationUtil.INSTANCE.toMainActivity();
                    } else { //No User is logged In
                        NavigationUtil.INSTANCE.toLogin();
                    }

                } else{ //No Internet connection
                    NavigationUtil.INSTANCE.toNoInternet();
                }
            }
        },3000);

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}