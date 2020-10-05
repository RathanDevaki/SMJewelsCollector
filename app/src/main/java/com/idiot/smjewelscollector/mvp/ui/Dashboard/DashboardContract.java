package com.idiot.smjewelscollector.mvp.ui.Dashboard;

import android.app.Activity;

public interface DashboardContract {

    interface View {
        void onSuccess(String message);

        void onFailure(String message);
    }

    interface Presenter {
        void scanCode();

        void createTransaction(String userID, String planName);

        void signOut(Activity activity);

    }



}
