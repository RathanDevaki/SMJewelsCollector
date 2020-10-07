package com.idiot.smjewelscollector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiot.smjewelscollector.databinding.ActivityMainBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtilMain;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity implements DashboardContract.View {

    DashboardContract.Presenter mPresenter;
    ActivityMainBinding mBinding;

    CountDownTimer countDownTimer;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mPresenter = new DashboardPresenter(this);
        init();
        mBinding.getRoot();

        //generateFirebaseKeys();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    private void init() {
        setupNavigator();

        //generateFirebaseKeys();

        NavigationUtilMain.INSTANCE.setUpHomeScreen();
    }

    private void setupNavigator() {
        NavigationUtilMain.INSTANCE.setupNavigator(this, getSupportActionBar(), getmBinding(), null);
    }

    public ActivityMainBinding getmBinding(){
        return (ActivityMainBinding) mBinding;
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }


    void generateFirebaseKeys() {


        final String[][] userArray = {
                {"1012087", "5000", "2019-10-23", "3510", "1.424", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012087", "5000", "2020-01-11", "3798", "1.316", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012067", "1000", "2020-01-13", "3777", "0.264", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012061", "1000", "2020-01-13", "3777", "0.264", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012062", "1000", "2020-01-13", "3777", "0.264", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012069", "1000", "2020-01-13", "3777", "0.264", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012079", "5000", "2020-01-14", "3744", "1.335", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012063", "1000", "2020-01-15", "3765", "0.265", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012064", "1000", "2020-01-15", "3769", "0.265", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012071", "5000", "2019-12-11", "3580", "1.396", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012071", "5000", "2020-02-03", "3884", "1.287", "WE HAVE RECEIVED 5000/-  ON THIS PERSON (KRISHNANANDA)", "planB"},
                {"1012098", "1000", "2019-10-09", "3615", "0.276", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012097", "1000", "2019-10-10", "3615", "0.276", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012084", "2000", "2019-10-31", "3621", "0.552", "WE HAVE RECEIVED 2000/-", "planB"},
                {"1012096", "1000", "2019-10-10", "3615", "0.276", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012096", "1000", "2019-07-12", "3621", "0.276", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012094", "1000", "2019-10-11", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012094", "1000", "2020-01-03", "3743", "0.267", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012094", "1000", "2020-02-03", "3820", "0.261", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012081", "1000", "2019-10-18", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012081", "1000", "2020-02-05", "3800", "0.263", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012091", "1000", "2019-10-12", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012091", "1000", "2019-12-06", "3621", "0.276", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012091", "1000", "2020-02-05", "3800", "0.263", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012020", "1000", "2020-02-05", "3817", "0.261", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012004", "1000", "2020-02-08", "3869", "0.258", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012099", "1000", "2019-10-11", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012099", "1000", "2019-01-03", "3743", "0.267", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012099", "1000", "2020-02-03", "3820", "0.261", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012093", "1000", "2019-10-14", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012100", "1000", "2019-10-15", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012092", "1000", "2019-10-17", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012090", "1000", "2019-10-19", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012088", "1000", "2019-10-21", "3605", "0.277", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012088", "1000", "2019-12-10", "3621", "0.276", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012085", "2000", "2019-10-31", "3621", "0.552", "WE HAVE RECEIVED 2000/-", "planB"},
                {"1012085", "2000", "2019-12-05", "3661", "0.546", "WE HAVE RECEIVED 2000/-", "planB"},
                {"1012087", "5000", "2020-02-10", "3858", "1.296", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012084", "2000", "2020-02-10", "3866", "0.517", "WE HAVE RECEIVED 2000/-", "planB"},
                {"1012070", "1000", "2019-12-13", "3621", "0.276 ", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012070", "1000", "2020-02-05", "3800", "0.263", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012083", "1000", "2019-11-04", "3661", "0.273", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012083", "1000", "2019-12-05", "3661", "0.273", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012083", "1000", "2020-01-31", "3660", "0.273", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012089", "1000", "2019-11-05", "3651", "0.273", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012086", "1000", "2019-11-05", "3651", "0.273", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012077", "5000", "2019-11-19", "3661", "1.365", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012077", "5000", "2020-01-24", "3780", "1.322", "WE HAVE RECEIVED 5000/-", "planB"},
                {"1012080", "1000", "2019-11-16", "3631", "0.275", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012080", "1000", "2020-01-10", "3770", "0.265", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012001", "1000", "2020-01-20", "3780", "0.264", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012002", "1000", "2020-01-20", "3780", "0.264", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012065", "1000", "2020-01-16", "3760", "0.265", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012006", "1000", "2020-02-08", "3800", "0.263", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012066", "2000", "2020-02-10", "3820", "0.523", "WE HAVE RECEIVED 2000/-", "planB"},
                {"1012005", "2000", "2020-02-10", "3820", "0.523", "WE HAVE RECEIVED 2000/-", "planB"},
                {"1012088", "1000", "2020-02-13", "3820", "0.261", "WE HAVE RECEIVED 1000/-", "planB"},
                {"1012085", "4000", "2020-02-14", "3860", "1.036", "WE HAVE RECEIVED 4000/-", "planB"},
                {"1012062", "1000", "2020-02-14", "3860", "0.269", "WE HAVE RECEIVED 1000", "planB"},
                {"1012061", "1000", "2020-02-14", "3860", "0.259", "WE HAVE RECEIVED 1000", "planB"},
                {"1012067", "1000", "2020-02-14", "3860", "0.259", "WE HAVE RECEIVED 1000", "planB"},
                {"1012086", "1000", "2020-02-14", "3863", "0.258", "WE HAVE RECEIVED 1000", "planB"},
                {"1012089", "1000", "2020-02-14", "3863", "0.258", "WE HAVE RECEIVED 1000", "planB"},
                {"1012097", "1000", "2020-02-15", "3894", "0.256", "WE HAVE RECEIVED 1000", "planB"},
                {"1012068", "2000", "2019-12-06", "3661", "0.546", "WE HAVE RECEIVED 2000", "planB"},
                {"1012003", "1000", "2019-10-09", "3615", "0.276", "WE HAVE RECEIVED 1000", "planB"},
                {"1012007", "1000", "2020-02-18", "3850", "0.256", "WE HAVE RECEIVED 1000", "planB"},
                {"1012008", "1000", "2020-02-18", "3850", "0.256", "WE HAVE RECEIVED 1000", "planB"},
                {"1012095", "1000", "2019-10-11", "3605", "0.277", "WE HAVE RECEIVED 1000", "planB"},
                {"1012095", "2000", "2019-12-07", "3621", "0.552", "WE HAVE RECEIVED 2000", "planB"},
                {"1012095", "1000", "2020-01-23", "3800", "0.263", "WE HAVE RECEIVED 1000", "planB"},
                {"1012068", "2000", "2020-02-20", "3961", "0.504", "WE HAVE RECEIVED 2000", "planB"},
                {"1012078", "2000", "2019-11-27", "3641", "0.549", "WE HAVE RECEIVED 2000", "planB"},
                {"1012078", "2000", "2020-02-21", "4026", "0.496", "WE HAVE RECEIVED 2000", "planB"},
                {"1012065", "1000", "2020-02-24", "4138", "0.241", "WE HAVE RECEIVED 1000", "planB"},
                {"1012095", "1000", "2020-02-24", "4132", "0.242", "WE HAVE RECEIVED 1000", "planB"},
                {"1012009", "1000", "2020-02-26", "4049", "0.246", "WE HAVE RECEIVED 1000", "planB"},
                {"1012080", "1000 ", "2020-02-28", "4040", "0.247", "WE HAVE RECEIVED 1000", "planB"},
                {"1012100", "1000", "2020-02-28", "4030", "0.248", "WE HAVE RECEIVED 1000", "planB"},
                {"1012094", "1000", "2020-02-28", "4043", "0.247", "WE HAVE RECEIVED 1000", "planB"},
                {"1012099", "1000", "2020-02-28", "4043", "0.247", "WE HAVE RECEIVED 1000", "planB"},
                {"1012077", "5000", "2020-02-28", "4033", "1.239", "WE HAVE RECEIVED 5000", "planB"},
                {"1012001", "1000", "2020-03-02", "3990", "0.250", "WE HAVE RECEIVED 1000", "planB"},
                {"1012002", "1000", "2020-03-02", "3990", "0.250", "WE HAVE RECEIVED 1000", "planB"},
                {"1012098", "2000", "2020-03-03", "4012", "0.498", "WE HAVE RECEIVED 2000", "planB"},
                {"1012006", "1000", "2020-03-03", "4010", "0.249", "WE HAVE RECEIVED 1000", "planB"},
                {"1011102", "1000", "2020-03-04", "4101", "0.243", "WE HAVE RECEIVED 1000", "planB"},
                {"1012020", "1000", "2020-03-07", "4180", "0.239", "WE HAVE RECEIVED 1000", "planB"},
                {"1012066", "2000", "2020-03-07", "4180", "0.478", "WE HAVE RECEIVED 2000", "planB"},
                {"1012005", "2000", "2020-03-07", "4180", "0.478    ", "WE HAVE RECEIVED 2000", "planB"},
                {"1012004", "1000", "2020-03-09", "4165", "0.240", "WE HAVE RECEIVED 1000", "planB"},
                {"1012069", "2000", "2020-03-11", "4128", "0.484", "WE HAVE RECEIVED 2000", "planB"},
                {"1012071", "5000", "2020-03-12", "4108", "1.217", "WE HAVE RECEIVED 5000", "planB"},
                {"1012070", "1000", "2020-03-12", "3992", "0.250", "WE HAVE RECEIVED 1000", "planB"},
                {"1012091", "1000", "2020-03-16", "3943", "0.253", "WE HAVE RECEIVED 500", "planB"},
                {"1012087", "5000", "2020-03-16", "3920", "1.275", "WE HAVE RECEIVED 5000", "planB"},
                {"1012100", "2000", "2020-03-16", "3920", "0.510", "WE HAVE RECEIVED 2000", "planB"},
                {"1012008", "1000", "2020-03-18", "3881", "0.257", "WE HAVE RECEIVED 1000", "planB"},
                {"1012007", "1000", "2020-03-18", "3881", "0.257", "WE HAVE RECEIVED 1000", "planB"},
                {"1012007", "1000", "2020-03-18", "3881", "0.257", "WE HAVE RECEIVED 1000", "planB"},
                {"1012062", "1000", "2020-03-18", "3881", "0.257", "WE HAVE RECEIVED 1000", "planB"},
                {"1012067", "1000", "2020-03-18", "3881", "0.257", "WE HAVE RECEIVED 1000", "planB"},
                {"1012061", "1000", "2020-03-18", "3881", "0.257", "WE HAVE RECEIVED 1000", "planB"},
                {"1012080", "1000", "2020-03-19", "3875", "0.258", "WE HAVE RECEIVED 1000", "planB"},
                {"1012084", "2000", "2020-03-19", "3856", "0.518", "We have received 2000 only ", "planB"},
                {"1012097", "1000", "2020-03-20", "3922", "0.254", "WE HAVE RECEIVED 1000", "planB"},
                {"1011102", "1000", "2020-03-20", "3922", "0.254", "WE HAVE RECEIVED 1000", "planB"},
                {"1012073", "1000", "2019-11-16", "3631", "0.275", "WE HAVE RECEIVED 1000", "planB"},
                {"1012073", "3000", "2020-02-14", "3864", "0.778", "WE HAVE RECEIVED 3000", "planB"},
                {"1012073", "1000", "2020-02-29", "3951", "0.252", "WE HAVE RECEIVED 1000", "planB"},
                {"1012082", "1000", "2019-10-19", "3605", "0.277", "WE HAVE RECEIVED 1000", "planB"},
                {"1012082", "3000", "2020-02-22", "4380", "0.683", "WE HAVE RECEIVED 3000", "planB"},
                {"1012009", "1000", "2020-04-09", "4167", "0.239", "WE HAVE RECEIVED 1000", "planB"},
                {"1011105", "1000", "2020-03-20", "3856", "0.259", "WE HAVE RECEIVED 1000", "planB"},
                {"1012098", "3000", "2020-05-08", "4400", "0.681", "WE HAVE RECEIVED 3000", "planB"},
                {"1011102", "3000", "2020-05-08", "4400", "0.681", "WE HAVE RECEIVED 3000", "planB"},
                {"1012008", "2000", "2020-05-14", "4406", "0.453", "WE HAVE RECEIVED 2000", "planB"},
                {"1012069", "2000", "2020-05-16", "4496", "0.444", "WE HAVE RECEIVED 2000", "planB"},
                {"1012005", "4000", "2020-05-16", "4496", "0.889", "WE HAVE RECEIVED 4000", "planB"},
                {"1012066", "4000", "2020-05-16", "4496", "0.889", "WE HAVE RECEIVED 4000", "planB"},
                {"1012099", "1000", "2020-05-16", "4501", "0.222", "WE HAVE RECEIVED 1000", "planB"},
                {"1012094", "1000", "2020-05-16", "4501", "0.222", "WE HAVE RECEIVED 1000", "planB"},
                {"1012087", "5000", "2020-05-18", "4546", "1.099", "WE HAVE RECEIVED 5000", "planB"},
                {"1012020", "1000", "2020-05-19", "4444", "0.224", "WE HAVE RECEIVED 1000", "planB"},
                {"1012100", "1000", "2020-05-21", "4471", "0.223", "WE HAVE RECEIVED 1000", "planB"},
                {"1011103", "1000", "2020-05-28", "4447", "0.223", "WE HAVE RECEIVED 1000", "planB"},
                {"1012092", "1000", "2020-05-29", "4437", "0.225", "WE HAVE RECEIVED 1000", "planB"},
                {"1012096", "2000", "2020-06-05", "4440", "0.450", "WE HAVE RECEIVED 2000", "planB"},
                {"1012087", "5000", "2020-06-09", "4414", "1.132", "WE HAVE RECEIVED 5000", "planB"},
                {"1012020", "1000", "2020-06-09", "4468", "0.223", "WE HAVE RECEIVED 1000", "planB"},
                {"1012062", "2000", "2020-06-11", "4500", "0.443", "WE HAVE RECEIVED 2000", "planB"},
                {"1012061", "2000", "2020-06-11", "4500", "0.443", "WE HAVE RECEIVED 2000", "planB"},
                {"1012067", "2000", "2020-06-11", "4500", "0.443", "WE HAVE RECEIVED 2000", "planB"},
                {"1012098", "3000", "2020-06-11", "4492", "0.667", "WE HAVE RECEIVED 3000", "planB"},
                {"1012099", "1000", "2020-06-11", "4500", "0.223", "WE HAVE RECEIVED 1000", "planB"},
                {"1012094", "1000", "2020-06-11", "4500", "0.223", "WE HAVE RECEIVED 1000", "planB"},
                {"1012083", "2000", "2020-06-12", "4501", "0.444", "WE HAVE RECEIVED 2000", "planB"},
                {"1012086", "1000", "2020-06-18", "4509", "0.221", "WE HAVE RECEIVED 1000", "planB"},
                {"1012089", "2000", "2020-06-18", "4509", "0.443", "WE HAVE RECEIVED 2000", "planB"},
                {"1012068", "2000", "2020-06-19", "4520", "0..442", "WE HAVE RECEIVED 2000", "planB"},
                {"1012100", "2000", "2020-06-20", "4565", "0.438", "WE HAVE RECEIVED 2000", "planB"},
                {"1012092", "1000", "2020-06-22", "4553", "0.219", "WE HAVE RECEIVED 1000", "planB"},
                {"1012091", "1000", "2020-06-22", "4563", "0.219", "WE HAVE RECEIVED 1000", "planB"},
                {"1012004", "2000", "2020-06-24", "4600", "0.434", "WE HAVE RECEIVED 2000", "planB"},
                {"1011103", "1000", "2020-06-30", "4596", "0.217", "WE HAVE RECEIVED 1000", "planB"},
                {"1012005", "2500", "2020-06-30", "4600", "0.543", "WE HAVE RECEIVED 2500", "planB"},
                {"1012066", "2500", "2020-06-30", "4600", "0.543", "WE HAVE RECEIVED 2500", "planB"},
                {"1011105", "1000", "2020-06-30", "4993", "0.217", "WE HAVE RECEIVED 1000", "planB"},
                {"1012098", "3000", "2020-07-01", "4640", "0.646", "WE HAVE RECEIVED 3000", "planB"},
                {"1012097", "3000", "2020-07-03", "4589", "0.653", "WE HAVE RECEIVED 3000", "planB"},
                {"1012020", "1000", "2020-07-06", "4580", "0.218", "WE HAVE RECEIVED 500", "planB"},
                {"1012085", "8000", "2020-07-06", "4596", "1.740", "WE HAVE RECEIVED 8000", "planB"},
                {"1012087", "5000", "2020-07-07", "4613", "1.083", "WE HAVE RECEIVED 5000", "planB"},
                {"1012086", "1000", "2020-07-09", "4680", "0.213", "WE HAVE RECEIVED 1000", "planB"},
                {"1012089", "1000", "2020-07-09", "4680", "0.213", "WE HAVE RECEIVED 1000", "planB"},
                {"1012099", "1000", "2020-07-10", "4663", "0.214", "WE HAVE RECEIVED 1000", "planB"},
                {"1012094", "1000", "2020-07-10", "4663", "0.214", "WE HAVE RECEIVED 1000", "planB"},
                {"1012091", "1000", "2020-07-14", "4664", "0.214", "WE HAVE RECEIVED 1000", "planB"},
                {"1012074", "1000", "2019-11-27", "3641", "0.274", "WE HAVE RECEIVED 1000", "planB"},
                {"1012074", "2000", "2020-02-10", "3864", "0.517", "WE HAVE RECEIVED 2000", "planB"},
                {"1012074", "2000", "2020-03-11", "4280", "0.467", "WE HAVE RECEIVED 2000", "planB"},
                {"1012074", "4000", "2020-04-10", "4400", "0.909", "WE HAVE RECEIVED 4000", "planB"},
                {"1012074", "4000", "2020-05-06", "4400", "0.909", "WE HAVE RECEIVED 4000", "planB"},
                {"1012072", "1000", "2019-11-27", "3641", "0.274", "WE HAVE RECEIVED 1000", "planB"},
                {"1012072", "2000", "2020-02-10", "3864", "0.517", "WE HAVE RECEIVED 2000", "planB"},
                {"1012072", "2000", "2020-03-11", "4280", "0.467", "WE HAVE RECEIVED 2000", "planB"},
                {"1012072", "4000", "2020-04-10", "4400", "0.909", "WE HAVE RECEIVED 4000", "planB"},
                {"1012072", "4000", "2020-05-06", "4400", "0.909", "WE HAVE RECEIVED 4000", "planB"},
                {"1012087", "5000", "2020-07-20", "4667", "1.071", "WE HAVE RECEIVED 5000", "planB"},
                {"1011105", "1000", "2020-07-21", "4691", "0.213", "WE HAVE RECEIVED 1000", "planB"},
                {"1012078", "6000", "2020-07-22", "4768", "1.258", "WE HAVE RECEIVED 6000", "planB"},
                {"1012069", "1000", "2020-07-22", "4770", "0.209", "WE HAVE RECEIVED 1000", "planB"},
                {"1012067", "1000", "2020-07-24", "4843", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012061", "1000", "2020-07-24", "4843", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012062", "1000", "2020-07-24", "4843", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012080", "4000", "2020-07-24", "4842", "0.826", "WE HAVE RECEIVED 4000", "planB"},
                {"1011103", "1000", "2020-07-24", "4841", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012061", "1000", "2020-07-24", "4850", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012062", "1000", "2020-07-24", "4850", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012067", "1000", "2020-07-24", "4850", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012083", "8000", "2020-07-24", "4858", "1.646", "WE HAVE RECEIVED 8000", "planB"},
                {"1012075", "1000", "2020-07-27", "4967", "0.201", "WE HAVE RECEIVED 1000", "planB"},
                {"1011111", "1000", "2020-07-27", "4972", "0.200", "WE HAVE RECEIVED 1000", "planB"},
                {"1012095", "1000", "2020-07-28", "4977", "0.200", "WE HAVE RECEIVED 1000", "planB"},
                {"1012100", "3000", "2020-07-28", "5010", "0.598", "WE HAVE RECEIVED 3000", "planB"},
                {"1011115", "2000", "2020-08-04", "5140", "0.389", "WE HAVE RECEIVED 2000", "planB"},
                {"1012001", "1000", "2020-08-11", "5200", "0.192", "WE HAVE RECEIVED 500", "planB"},
                {"1012002", "1000", "2020-08-11", "5200", "0.192", "WE HAVE RECEIVED 1000", "planB"},
                {"1012098", "2000", "2020-08-13", "5016", "0.398", "WE HAVE RECEIVED 2000", "planB"},
                {"1012087", "5000", "2020-08-13", "5015", "0.997", "WE HAVE RECEIVED 5000", "planB"},
                {"1012099", "1000", "2020-08-13", "5015", "0.199", "WE HAVE RECEIVED 1000", "planB"},
                {"1012094", "1000", "2020-08-13", "5015", "0.199", "WE HAVE RECEIVED 1000", "planB"},
                {"1012005", "5040", "2020-08-17", "5040", "1.000", "WE HAVE RECEIVED 5040", "planB"},
                {"1012066", "5040", "2020-08-17", "5040", "1.000", "WE HAVE RECEIVED 5040", "planB"},
                {"1012068", "2000", "2020-08-17", "5050", "0.396", "WE HAVE RECEIVED 2000", "planB"},
                {"1012097", "2000", "2020-08-19", "5050", "0.396", "WE HAVE RECEIVED 2000", "planB"},
                {"1012009", "4000", "2020-08-19", "5046", "0.792", "WE HAVE RECEIVED 4000", "planB"},
                {"1012100", "5000", "2020-08-19", "5050", "0.990", "WE HAVE RECEIVED 5000", "planB"},
                {"1011105", "1000", "2020-08-28", "4830", "0.207", "WE HAVE RECEIVED 1000", "planB"},
                {"1012074", "2000", "2020-08-31", "4863", "0.411", "WE HAVE RECEIVED 2000", "planB"},
                {"1012072", "2000", "2020-08-31", "4863", "0.411", "WE HAVE RECEIVED 2000", "planB"},
                {"1011106", "1000", "2020-09-01", "4900", "0.204", "WE HAVE RECEIVED 1000", "planB"},
                {"1011115", "3000", "2020-09-02", "4840", "0.619", "WE HAVE RECEIVED 3000", "planB"},
                {"1012091", "2000", "2020-09-02", "4840", "0.413", "WE HAVE RECEIVED 2000", "planB"},
                {"1012075", "1000", "2020-09-04", "4838", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012087", "5000", "2020-09-05", "4830", "1.035", "WE HAVE RECEIVED 5000", "planB"},
                {"1011103", "1000", "2020-09-07", "4830", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012081", "2000", "2020-09-08", "4856", "0.411", "WE HAVE RECEIVED 2000", "planB"},
                {"1012098", "3000", "2020-09-08", "4835", "0.620", "WE HAVE RECEIVED 3000", "planB"},
                {"1012069", "1000", "2020-09-09", "4870", "0.205", "WE HAVE RECEIVED 1000", "planB"},
                {"1012092", "1000", "2020-09-09", "4860", "0.205", "WE HAVE RECEIVED 1000", "planB"},
                {"1011104", "2000", "2020-09-09", "4870", "0.410", "WE HAVE RECEIVED 2000", "planB"},
                {"1012099", "1000", "2020-09-09", "4850", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1012094", "1000", "2020-09-09", "4850", "0.206", "WE HAVE RECEIVED 1000", "planB"},
                {"1011106", "1000", "2020-09-14", "4875", "0.205", "WE HAVE RECEIVED 1000", "planB"},
                {"1012097", "2000", "2020-09-16", "4910", "0.407", "WE HAVE RECEIVED 2000", "planB"},
                {"1011105", "1000", "2020-09-17", "4880", "0.205", "WE HAVE RECEIVED 1000", "planB"},
                {"1012072", "2000", "2020-09-18", "4890", "0.409", "WE HAVE RECEIVED 2000", "planB"},
                {"1012074", "2000", "2020-09-18", "4890", "0.409", "WE HAVE RECEIVED 2000", "planB"},
                {"1012086", "1000", "2020-09-19", "4930", "0.203", "WE HAVE RECEIVED 1000", "planB"},
                {"1012089", "1000", "2020-09-19", "4930", "0.203", "WE HAVE RECEIVED 1000", "planB"},
                {"1011111", "1000", "2020-09-21", "4910", "0.203", "WE HAVE RECEIVED 1000", "planB"},
                {"1012100", "2000", "2020-09-23", "4780", "0.418", "WE HAVE RECEIVED 2000", "planB"},
                {"1012068", "2000", "2020-09-28", "4720", "0.423", "WE HAVE RECEIVED 2000", "planB"}
        };

        for (i = 0; i < userArray.length; i++) {

            getAndUpdateData(userArray[i][0], userArray[i][1], userArray[i][2], userArray[i][5], userArray[i][6], userArray[i][3], userArray[i][4]);
//
//
//            String puskKey = databaseReference.push().getKey();
//            HashMap<String,Object> userMap = new HashMap<>();
//
//
//            databaseReference.child("PlanA").child("UsersList").child("Set1").child(puskKey).setValue(userMap);
//
//            HashMap<String,Object> userMap2 = new HashMap<>();
//            userMap2.put("UserID",puskKey);
//            userMap2.put("PlanName","PlanA");
//            userMap2.put("SetName","Set1");
//            userMap2.put("Phone",userArray[i][2]);
//
//            databaseReference.child("UsersList").child(userArray[i][0]).setValue(userMap2);

//            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//            HashMap<String,Object> userMap1 = new HashMap<>();
//            userMap1.put("Name",userArray[i][1]);
//            userMap1.put("ID",userArray[i][0]);
//            userMap1.put("Phone",userArray[i][2]);
//            userMap1.put("EmailID",userArray[i][3]);
//            userMap1.put("Address",userArray[i][4]);
//            userMap1.put("PinCode",userArray[i][5]);
//            userMap1.put("DOB",userArray[i][6]);
//            userMap1.put("Plan",userArray[i][7]);
//            userMap1.put("PAN",userArray[i][8]);
//            userMap1.put("Aadhar",userArray[i][9]);
//            userMap1.put("Nominee",userArray[i][10]);
//            userMap1.put("NomineeRelationship",userArray[i][11]);
//            userMap1.put("NomineePhone",userArray[i][12]);
//            userMap1.put("TotalAmount",0);
//
//            DatabaseReference databaseReference = firebaseDatabase.getReference();
//            String pushKey = databaseReference.push().getKey();
//            databaseReference.child("PlanB").child("UsersList").child(pushKey).setValue(userMap1);
//
//            HashMap<String,Object> userMap2 = new HashMap<>();
//            userMap2.put("Name",userArray[i][1]);
//            userMap2.put("Phone",userArray[i][2]);
//            userMap2.put("UserID",pushKey);
//
//            databaseReference.child("UsersList").child(userArray[i][0]).setValue(userMap2);

        }

    }

    private void getAndUpdateData(final String ID, final String amount, final String date, final String comments, String plan, final String goldRate, final String goldCount) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        Log.v("TAg", "ID=>" + ID);

        databaseReference.child("UsersList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(ID)) {
                    String userKey = snapshot.child(ID).child("UserID").getValue(String.class);
                    HashMap<String, String> transactionMap = new HashMap<>();
                    transactionMap.put("Amount", amount);
                    transactionMap.put("Date", date);
                    transactionMap.put("Comments", comments);
                    transactionMap.put("GoldRate", goldRate);
                    transactionMap.put("GoldCount", goldCount);


                    HashMap<String, String> transactionMap2 = new HashMap<>();
                    transactionMap2.put("PlanName", "PlanA");
                    transactionMap2.put("UserID", ID);
                    transactionMap2.put("Amount", amount);
                    transactionMap2.put("Date", date);
                    transactionMap2.put("Comments", comments);
                    transactionMap2.put("GoldRate", goldRate);
                    transactionMap2.put("GoldCount", goldCount);


                    if (userKey == null) {
                    } else {
                        displayID(userKey);
                        pushData(userKey, transactionMap, transactionMap2);
                    }

                    //pushData(userKey,transactionMap,transactionMap2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void displayID(String ID) {
        Log.v("TAG", "USerKey=>" + ID);
    }


    private void pushData(final String userKey, HashMap<String, String> transactionMap, HashMap<String, String> transactionMap2) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

//        databaseReference.child("PlanA").child("UsersList").child("Set1")
//                .child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////                databaseReference.child("PlanA").child("UsersList").child("Set1")
////                        .child(userKey).child("CompletedMonths").setValue(0);
////                databaseReference.child("PlanA").child("UsersList").child("Set1")
////                        .child(userKey).child("TotalAmount").setValue(0);
//
//                long count = snapshot.child("Transactions").getChildrenCount();
//
//                long completedMonths = snapshot.child("CompletedMonths").getValue(Long.class);
//                completedMonths = completedMonths+1;
//                long totalAmount = count*500;
//
//                databaseReference.child("PlanA").child("UsersList").child("Set1")
//                        .child(userKey).child("CompletedMonths").setValue(count);
//                databaseReference.child("PlanA").child("UsersList").child("Set1")
//                        .child(userKey).child("TotalAmount").setValue(totalAmount);
//
//                Log.v("TAG","UserID Changed=>"+userKey);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        databaseReference.child("PlanB").child("UsersList")
                .child(userKey).child("Transactions").push().setValue(transactionMap);

        databaseReference.child("TransactionDetails").push().setValue(transactionMap2);

    }

}