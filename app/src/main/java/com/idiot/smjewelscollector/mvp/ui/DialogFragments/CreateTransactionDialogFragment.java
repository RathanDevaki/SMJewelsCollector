package com.idiot.smjewelscollector.mvp.ui.DialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
    String ID;
    String setName;

    DatabaseReference databaseReference;

    String amount;
    Double grams;


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
        ID = getArguments().getString("ID");
        userID = getArguments().getString("UserID");

        if (planName.compareToIgnoreCase("PlanA") == 0) {
            setName = getArguments().getString("SetName");
        }

        mBinding.userPlanCreateTransaction.setText(planName);
        mBinding.userIdCreateTransaction.setText(ID);

        mBinding.userDateCreateTransaction.setEnabled(false);
        mBinding.userDateCreateTransaction.setText(getDateAndTime());

        mBinding.createTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTransaction();

                sendSMSToUser("8123022771", "500", "12/08/2020");
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        if (planName.compareToIgnoreCase("PlanA") == 0) {
            databaseReference = firebaseDatabase.getReference().child(planName).child("UsersList").child(setName).child(userID);
        } else {
            databaseReference = firebaseDatabase.getReference().child(planName).child("UsersList").child(userID);
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.userNameCreateTransaction.setText(snapshot.child("Name").getValue(String.class));
                if (snapshot.hasChild("ProfilePhoto")) {
                    Glide
                            .with(getContext())
                            .load(snapshot.child("ProfilePhoto").getValue(String.class))
                            .into(mBinding.userPhotoCreateTransaction);
                } else {
                    Glide
                            .with(getContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/sm-jewels.appspot.com/o/img_162044.png?alt=media&token=c5445416-61d0-4ea7-90e7-a77a5e65cd09")
                            .into(mBinding.userPhotoCreateTransaction);
                }
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

        amount = mBinding.userAmountCreateTransaction.getText().toString();
        String comment = mBinding.userCommentsCreateTransaction.getText().toString();
        final String date = mBinding.userDateCreateTransaction.getText().toString();

        HashMap<String, String> paymentMap = new HashMap<>();
        paymentMap.put("Amount", amount);
        paymentMap.put("Comments", comment);
        paymentMap.put("Date", date);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        if (planName.compareToIgnoreCase("PlanA") == 0) {
            DatabaseReference databaseReference1 = firebaseDatabase.getReference().child(planName)
                    .child("UsersList").child(setName).child(userID);

            databaseReference1.child("Transactions").push().setValue(paymentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    sendSMSToUser(phone, amount, date);
                    updateTransactionInfo();
                }
            });
        } else if (planName.compareToIgnoreCase("PlanB") == 0) {
            DatabaseReference databaseReference1 = firebaseDatabase.getReference().child(planName)
                    .child("UsersList").child(userID);
            databaseReference1.child("Transactions").push().setValue(paymentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toasty.success(getContext(), "Payment Successfull").show();
                }
            });

            DatabaseReference databaseReference2 = firebaseDatabase.getReference().child("PlanB").child("GoldRate");
            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final Double goldRate = snapshot.getValue(Double.class);
                    int _amount = Integer.parseInt(amount);
                    grams = _amount / goldRate;

                    final DatabaseReference databaseReference3 = firebaseDatabase.getReference().child("PlanB")
                            .child("UsersList").child(userID);
                    databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild("GoldSaved")) {
                                Double goldSaved = snapshot.child("GoldSaved").getValue(Double.class);
                                goldSaved = goldSaved + grams;
                                databaseReference3.child("GoldSaved").setValue(goldSaved);
                            } else {
                                databaseReference3.child("GoldSaved").setValue(grams);

                            }

                            dismiss();
                            sendSMSToUser(phone, amount, date);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        //Update Transactions info in collector node
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("CollectorsInfo")
                .child(preferences.getString("UserID", ""));
        paymentMap.put("UserID", ID);
        databaseReference1.child("Transactions").push().setValue(paymentMap);

    }

    private String sendSMSToUser(String phone, String amount, String date) {

        try {

            //App Message
            String appMessage = "Dear " + mBinding.userNameCreateTransaction + ",\n" + "Payment Recieved successfully  of Rs." + amount + "/-  toward your plan.\nDated: " + date + "\nRegargs SMJW";

            // Construct data
            String apiKey = "apikey=" + "AcGtwQxa1rc-qwsAmGEK7uZZThc0hOehHa1uwjRWYw";
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
            //To Update Completed Months Etc
            Toasty.success(getContext(), "SMS sentt to:" + phone).show();
            return stringBuffer.toString();

        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            Log.v("TAG", "SMS ERROR=>" + e);
            Toasty.error(getContext(), e.getMessage()).show();
            return "Error " + e;
        }
    }

    private void updateTransactionInfo() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(planName);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                long planAmount = snapshot.child("PlanAmount").getValue(long.class);
                if (snapshot.child("UsersList").child("Set1").child(userID).hasChild("CompletedMonths")) {
                    long completedMonths = snapshot.child("UsersList").child("Set1").child(userID).child("CompletedMonths").getValue(Long.class);
                    completedMonths = completedMonths + 1;
                    databaseReference.child("UsersList").child("Set1").child(userID).child("CompletedMonths").setValue(completedMonths);
                    databaseReference.child("UsersList").child("Set1").child(userID).child("TotalAmount").setValue((int) completedMonths * planAmount);
                    databaseReference.child("UsersList").child("Set1").child(userID).child("TotalTransactions").setValue(completedMonths);

                } else {
                    databaseReference.child("UsersList").child("Set1").child(userID).child("CompletedMonths").setValue(1);
                    databaseReference.child("UsersList").child("Set1").child(userID).child("TotalAmount").setValue(planAmount);
                    databaseReference.child("UsersList").child("Set1").child(userID).child("TotalTransactions").setValue(1);
                }

                Toasty.success(getContext(), "Transaction Successfull").show();
                dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
