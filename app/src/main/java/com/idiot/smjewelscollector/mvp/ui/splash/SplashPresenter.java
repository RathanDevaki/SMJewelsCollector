package com.idiot.smjewelscollector.mvp.ui.splash;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SplashPresenter implements SplashContract.Presenter {

    SplashContract.View view;
    FirebaseAuth mAuth;
    private String mVerificationId;
    Context _context;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public boolean checkInternet(Context context) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public boolean checkLogin() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            Log.v("Logd","Logged");
            //User Logged in Already
            return true;
        } else{
            Log.v("Err","Not Logged");
            //No User is logged in


            return false;
        }
    }

    @Override
    public void showDialog(Context context) {

    }

    @Override
    public void hideDialog() {

    }



}
