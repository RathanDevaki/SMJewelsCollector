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
                {"2012001", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012001", "500", "18/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012001", "500", "20/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012001", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012001", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012001", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012002", "500", "18/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012002", "500", "20/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "24/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012002", "500", "22/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012003", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012003", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012003", "500", "09/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012003", "500", "18/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012004", "500", "18/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012004", "500", "20/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "02/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012004", "500", "25/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012005", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012005", "500", "15/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012005", "500", "25/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012005", "500", "25/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012006", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012006", "500", "21/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012006", "500", "20/03/2020", "We have received 500 only ", "PlanA"},
                {"2012006", "500", "20/03/2020", "We have received 500 only ", "PlanA"},
                {"2012007", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012007", "500", "17/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012007", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012007", "500", "20/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012007", "500", "25/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012008", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012008", "500", "17/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012008", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012009", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012009", "500", "17/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012009", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012010", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012010", "500", "16/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012010", "500", "20/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012010", "500", "14/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012010", "500", "14/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012010", "500", "14/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012011", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012011", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012011", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012011", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012011", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012011", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012011", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012011", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012012", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012012", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012012", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012012", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012012", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012012", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012012", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012012", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012013", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012013", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012013", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012013", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012013", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012013", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012013", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012013", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012014", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012014", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012014", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012014", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012014", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012014", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012014", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012014", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012015", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012015", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012015", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012015", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012015", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012015", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012015", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012015", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012016", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012016", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012016", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012016", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012016", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012016", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012016", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012016", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012017", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012017", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012017", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012017", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012017", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012017", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012017", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012017", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012018", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012018", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012018", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012018", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012018", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012018", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012018", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012018", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012019", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012019", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012019", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012019", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012019", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012019", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012019", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012019", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012020", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012020", "500", "31/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012020", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012020", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012020", "500", "09/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012020", "500", "26/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012020", "500", "01/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012020", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012021", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012021", "500", "27/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012021", "500", "04/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012021", "500", "30/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012021", "500", "30/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012021", "500", "30/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012021", "500", "30/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012021", "500", "30/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012022", "500", "09/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012022", "500", "18/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "18/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "18/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "18/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "13/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "24/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "14/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012022", "500", "18/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012023", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012023", "500", "16/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012023", "500", "25/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012023", "500", "25/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012024", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012025", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012025", "500", "09/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012025", "500", "04/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012025", "500", "01/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012025", "500", "01/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012025", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012025", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012025", "500", "18/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012025", "500", "18/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012026", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012026", "500", "01/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012026", "500", "16/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "22/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "06/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012026", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012027", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012027", "500", "09/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012027", "500", "09/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012027", "500", "09/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012028", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012029", "500", "06/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012029", "500", "06/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012029", "500", "05/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012029", "500", "07/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012029", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012029", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012029", "500", "09/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012029", "500", "08/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012029", "500", "03/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012030", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012030", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012031", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012031", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012031", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012031", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012031", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012031", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012031", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012031", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012031", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012032", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012032", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012032", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012032", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012032", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012032", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012032", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012032", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012032", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012033", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012033", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012033", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012033", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012033", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012033", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012033", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012033", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012033", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012034", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012034", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012034", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012034", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012034", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012034", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012034", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012034", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012034", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012035", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012035", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012035", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012035", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012035", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012035", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012035", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012035", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012035", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012036", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012036", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012036", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012036", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012036", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012036", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012036", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012036", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012036", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012037", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012037", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012037", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012037", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012037", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012037", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012037", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012037", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012037", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012038", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012038", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012038", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012038", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012038", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012038", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012038", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012038", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012038", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012039", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012039", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012039", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012039", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012039", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012039", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012039", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012039", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012039", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012040", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012040", "500", "03/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012040", "500", "03/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012040", "500", "28/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012040", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012040", "500", "11/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012040", "500", "10/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012040", "500", "13/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012040", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012041", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012041", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012041", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012042", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012042", "500", "17/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012043", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012043", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012043", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012044", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012044", "500", "17/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012045", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012045", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012045", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012046", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012046", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012046", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012047", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012047", "500", "09/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012047", "500", "15/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012047", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012047", "500", "12/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012047", "500", "12/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012047", "500", "17/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012047", "500", "03/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012048", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012048", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012048", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012049", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012049", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012050", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012050", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012051", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012051", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012051", "500", "26/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "27/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "27/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "08/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "08/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "11/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "27/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012051", "500", "24/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012052", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012052", "500", "21/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012052", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012052", "500", "03/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012052", "500", "03/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012053", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012053", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012053", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012053", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012053", "500", "16/06/2020", "We have received 500 ", "PlanA"},
                {"2012054", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012054", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012054", "500", "24/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012054", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012054", "500", "16/06/2020", "We have received 500 ", "PlanA"},
                {"2012054", "500", "16/06/2020", "We have received 500 ", "PlanA"},
                {"2012054", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012055", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012055", "500", "21/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012055", "500", "29/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012055", "500", "03/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012055", "500", "03/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012056", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012056", "500", "16/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012056", "500", "25/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012057", "500", "07/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012057", "500", "21/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012057", "500", "21/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012057", "500", "21/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012058", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012058", "500", "22/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012058", "500", "07/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012058", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012058", "500", "13/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012058", "500", "13/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012058", "500", "13/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012058", "500", "07/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012058", "500", "07/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012059", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012059", "500", "16/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012059", "500", "25/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012059", "500", "25/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012060", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012060", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012060", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012060", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012060", "500", "16/06/2020", "We have received 500 ", "PlanA"},
                {"2012061", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012061", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012061", "500", "25/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012061", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012061", "500", "17/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012061", "500", "17/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012061", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012061", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012062", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012062", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012062", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012062", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012062", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012062", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012062", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012062", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012063", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012063", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012063", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012063", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012063", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012063", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012063", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012063", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012064", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012064", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012064", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012065", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012065", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012065", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012065", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012065", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012065", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012065", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012065", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012066", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012066", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012066", "500", "25/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012066", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012066", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012066", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012066", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012066", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012067", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012067", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012067", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012068", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012068", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012068", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012068", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012068", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012068", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012068", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012068", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012069", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012069", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012069", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012069", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012069", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012069", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012069", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012070", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012070", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "16/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "19/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "26/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012070", "500", "09/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012071", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012071", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012071", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012071", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012072", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012072", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012072", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012072", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012073", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012073", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012073", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012073", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012074", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012074", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012074", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012074", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012075", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012075", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012075", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012075", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012076", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012076", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012076", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012076", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012077", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012077", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012077", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012078", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012078", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012078", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012079", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012079", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012079", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012080", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012080", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012080", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012081", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012081", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012081", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012082", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012082", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012082", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012083", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012083", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012083", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012084", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012084", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012084", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012085", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012085", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012085", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012086", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012086", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012086", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012087", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012087", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012087", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012088", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012088", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012088", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012089", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012089", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012089", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012090", "500", "20/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012090", "500", "19/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "20/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "29/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "29/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012090", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012091", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012091", "500", "21/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012091", "500", "21/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012091", "500", "21/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012092", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012092", "500", "18/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012092", "500", "20/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012092", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012092", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012092", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012092", "500", "20/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012093", "500", "14/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012093", "500", "17/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012093", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012094", "500", "11/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012094", "500", "17/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "27/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "27/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012094", "500", "23/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012095", "500", "12/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012095", "500", "12/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012095", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "22/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012095", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012096", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012096", "500", "15/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012096", "500", "17/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012096", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012096", "500", "01/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012096", "500", "01/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012096", "500", "01/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012096", "500", "17/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012097", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012097", "500", "15/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012097", "500", "15/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012097", "500", "18/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012097", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012097", "500", "24/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012098", "500", "12/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012098", "500", "12/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012098", "500", "20/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "20/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "22/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "19/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012098", "500", "26/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012099", "500", "13/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012099", "500", "05/02/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012099", "500", "06/03/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "19/05/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "09/06/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "14/07/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "12/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012099", "500", "15/09/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012100", "500", "20/12/2019", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012100", "500", "07/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012100", "500", "29/01/2020", "WE HAVE RECEIVED 500/-", "PlanA"},
                {"2012100", "500", "25/02/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012100", "500", "24/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012100", "500", "24/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012100", "500", "24/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012100", "500", "24/08/2020", "WE HAVE RECEIVED 500", "PlanA"},
                {"2012100", "500", "14/09/2020", "WE HAVE RECEIVED 500", "PlanA"}

        };

//        for (i=0;i<userArray.length;i++){
//
//            getAndUpdateData(userArray[i][0],userArray[i][1],userArray[i][2],userArray[i][3],userArray[i][4]);
//
//
////            String puskKey = databaseReference.push().getKey();
////            HashMap<String,Object> userMap = new HashMap<>();
////
////
////            databaseReference.child("PlanA").child("UsersList").child("Set1").child(puskKey).setValue(userMap);
////
////            HashMap<String,Object> userMap2 = new HashMap<>();
////            userMap2.put("UserID",puskKey);
////            userMap2.put("PlanName","PlanA");
////            userMap2.put("SetName","Set1");
////            userMap2.put("Phone",userArray[i][2]);
////
////            databaseReference.child("UsersList").child(userArray[i][0]).setValue(userMap2);
//
//        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("UsersList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("TAG", "UsersCount=>" + snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); // Getting users count

        databaseReference.child("TransactionDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("TAG", "TransactionDetails=>" + snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAndUpdateData(final String ID, final String amount, final String date, final String comments, String plan) {

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


                    HashMap<String, String> transactionMap2 = new HashMap<>();
                    transactionMap2.put("PlanName", "PlanA");
                    transactionMap2.put("UserID", ID);
                    transactionMap2.put("Amount", amount);
                    transactionMap2.put("Date", date);
                    transactionMap2.put("Comments", comments);


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

//        databaseReference.child("PlanA").child("UsersList").child("Set1")
//                .child(userKey).child("Transactions").push().setValue(transactionMap);
//
//        databaseReference.child("TransactionDetails").push().setValue(transactionMap2);

    }

}