package com.idiot.smjewelscollector.mvp.ui.Dashboard;

public class DashboardPresenter implements DashboardContract.Presenter {

    DashboardContract.View view;

    public DashboardPresenter(DashboardContract.View view) {
        this.view = view;
    }

    @Override
    public void scanCode() {

    }

    @Override
    public void createTransaction(String userID, String planName) {

    }
}
