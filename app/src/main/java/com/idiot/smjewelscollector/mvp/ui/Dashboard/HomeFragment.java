package com.idiot.smjewelscollector.mvp.ui.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentHomeBinding;
import com.idiot.smjewelscollector.mvp.ui.CreateUser.CreateUserFragment;

public class HomeFragment extends Fragment implements DashboardContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    FragmentHomeBinding mBinding;
    DashboardPresenter mPresenter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        mPresenter = new DashboardPresenter(this);
        Init();
        return mBinding.getRoot();


    }

    private void Init() {

        CollectionListFragment collectionListFragment = new CollectionListFragment();
        loadFragment(collectionListFragment);
        mBinding.homeBottomNavigation.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public void onSuccess(String message)
    {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;

        switch (item.getItemId()){
            case R.id.collection_list_menu:
                fragment = new CollectionListFragment();
                loadFragment(fragment);
                return true;
            case R.id.transactions_list_menu:
                return true;
            case R.id.scan_menu:
                fragment = new ScanFragment();
                loadFragment(fragment);
                return true;
            case R.id.create_user_menu:
                fragment = new CreateUserFragment();
                loadFragment(fragment);
                return true;
            case R.id.profile_menu:
                fragment = new AccountFragment();
                loadFragment(fragment);
                return true;

        }

        return false;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.bottom_navigation_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}