package com.idiot.smjewelscollector.mvp.ui.DialogFragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCreateTransactionDialogBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


public class CreateTransactionDialogFragment extends DialogFragment implements DashboardContract.View {

    FragmentCreateTransactionDialogBinding mBinding;
    DashboardPresenter mPresenter;

    String phone;
    String planName;
    String userID;


    public CreateTransactionDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_transaction_dialog, container, false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        planName = getArguments().getString("PlanName");
        phone = getArguments().getString("Phone");
        String ID = getArguments().getString("ID");
        userID = getArguments().getString("UserID");

        Log.v("TAG","PlanName=>"+planName + "\n"
         + "Phone=>" + phone + "\n"
        + "ID=>" + ID + "\n"
        + "userID=>" + userID);

        mBinding.userPlanCreateTransaction.setText(planName);
        mBinding.userIdCreateTransaction.setText(ID);

        mBinding.userDateCreateTransaction.setEnabled(false);
        mBinding.userDateCreateTransaction.setText(getDateAndTime());

        mBinding.createTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTransaction();
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child(planName).child("UsersList").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.userNameCreateTransaction.setText(snapshot.child("Name").getValue(String.class));
                Glide
                        .with(getContext())
                        .load(snapshot.child("ProfilePhoto").getValue(String.class))
                        .into(mBinding.userPhotoCreateTransaction);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String getDateAndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy, HH:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;

    }

    private void createTransaction() {

        final String amount = mBinding.userAmountCreateTransaction.getText().toString();
        String comment = mBinding.userCommentsCreateTransaction.getText().toString();
        final String date = mBinding.userDateCreateTransaction.getText().toString();

        HashMap<String,String> paymentMap = new HashMap<>();
        paymentMap.put("Amount",amount);
        paymentMap.put("Comments",comment);
        paymentMap.put("Date",date);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(planName)
                .child("UsersList").child(userID);
        databaseReference.child("Transactions").push().setValue(paymentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sendSMSToUser(phone,amount,date);
            }
        });


    }

    private String sendSMSToUser(String phone, String amount, String date) {
        Toasty.success(getContext(),"SMS snet to:"+phone).show();
        try {

            //App Message
            String appMessage = "Dear " + mBinding.userNameCreateTransaction + ",\n" + "Payment Recieved successfully  of Rs." + amount + "/-  toward your plan.\nDated: " + date + "\nRegargs SMJW";

            // Construct data
            String apiKey = "apikey=" + "AcGtwQxa1rc-YJSR88WGwMRk3mQhm4i8O9CxoehJ01";
            String message = "&message=" + appMessage;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "91" + phone;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            Log.v("TAG","SMS ERROR=>" + e);
            Toasty.error(getContext(),e.getMessage()).show();
            return "Error "+e;
        }
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
