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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiot.smjewelscollector.MainActivity;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCreateTransactionDialogBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtil;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtilMain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class CreateTransactionDialogFragment extends DialogFragment implements DashboardContract.View {

    FragmentCreateTransactionDialogBinding mBinding;
    DashboardPresenter mPresenter;

    String phone;
    String planName;
    String userID;
    String ID;
    String setName;

    long instAmt = 0;
    long instPeriod = 0;
    DatabaseReference databaseReference;

    String amount;
    Double grams;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

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
        checkUserStatus();
        mBinding.userPlanCreateTransaction.setText(planName);
        mBinding.userIdCreateTransaction.setText(ID);
        // mBinding.userAmountCreateTransaction.setHint("Payable amount is 500");
        mBinding.userDateCreateTransaction.setEnabled(false);
        mBinding.userDateCreateTransaction.setText(getDateAndTime());

        mBinding.createTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.createTransactionBtn.setVisibility(View.GONE);
                mBinding.progressBar.setVisibility(View.VISIBLE);
                createTransaction();

                sendSMSToUser(phone, amount, getDateAndTime());
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //only for planA

        if (planName.compareToIgnoreCase("PlanA") == 0) {
            databaseReference = firebaseDatabase.getReference().child(planName).child("UsersList").child(setName).child(userID);
        }
        //For planB and C
        else {
            databaseReference = firebaseDatabase.getReference().child(planName).child("UsersList").child(userID);
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.userNameCreateTransaction.setText(snapshot.child("Name").getValue(String.class));
                if (snapshot.hasChild("ProfilePhoto")) {
                    Glide
                            .with(Objects.requireNonNull(getContext()))
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


    private void checkUserStatus() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(planName);
        final DatabaseReference databaseReference2 = firebaseDatabase.getReference();
        Log.v("user IDDD", userID);
        Log.v("IDDD", ID);
        if (planName.compareToIgnoreCase("PlanA") == 0) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long planAmount = snapshot.child("PlanAmount").getValue(long.class);
                    if (snapshot.child("UsersList").child("Set1").child(userID).hasChild("CompletedMonths")) {
                        long completedMonths = snapshot.child("UsersList").child("Set1").child(userID).child("CompletedMonths").getValue(Long.class);
                        Log.v("user IDDD", userID);
                        long totalMonths = snapshot.child("UsersList").child("Set1").child(userID).child("InstallmentPeriod").getValue(Long.class);
                        if (totalMonths == completedMonths) {
                            databaseReference.child("UsersList").child("Set1").child(userID).child("PlanCompletionStatus").setValue("Completed");
                            databaseReference2.child("PlanCompletionStatus").child(ID).setValue("Completed");
                            mBinding.allowPayment.setVisibility(View.GONE);
                            mBinding.userCompleted.setVisibility(View.VISIBLE);
                            Toasty.success(getContext(), "The plan has completed", Toasty.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (planName.compareToIgnoreCase("PlanB") == 0) {
            Log.v("user IDDD", userID);
           /* databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {*/

            final DatabaseReference databaseReferenceI = firebaseDatabase.getReference().child("PlanB").child("UsersList").child(userID);
            databaseReferenceI.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long completedMonths = snapshot.child("CompletedMonths").getValue(Long.class);

                    if (snapshot.hasChild("InstallmentPeriod")) {
                        //instAmt = snapshot.child("InstallmentAmount").getValue(Long.class);
                        instPeriod = snapshot.child("InstallmentPeriod").getValue(Long.class);
                        Log.v("inst period", String.valueOf(instPeriod));
                    } else {
                        // databaseReferenceI.child("InstallmentAmount").setValue(0);
                        databaseReferenceI.child("InstallmentPeriod").setValue(0);
                    }
                    if (instPeriod == 0) {
                        databaseReferenceI.child("PlanCompletionStatus").setValue("NA");
                        mBinding.allowPayment.setVisibility(View.GONE);
                        mBinding.userWarning.setVisibility(View.VISIBLE);
                    } else if (instPeriod == completedMonths && instPeriod != 0) {
                        databaseReferenceI.child("PlanCompletionStatus").setValue("Completed");

                        //------illi nodo
                        databaseReference2.child("PlanCompletionStatus").child(ID).setValue("Completed");
                        mBinding.allowPayment.setVisibility(View.GONE);
                        mBinding.userCompleted.setVisibility(View.VISIBLE);
                        Toasty.success(getContext(), "The plan has completed", Toasty.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


    }

    private void createTransaction() {

        final DatabaseReference databaseReferenceAdmin = firebaseDatabase.getReference().child("TransactionDetails");
        final String pushKey;
        amount = mBinding.userAmountCreateTransaction.getText().toString();
        String comment = mBinding.userCommentsCreateTransaction.getText().toString();
        final String date = mBinding.userDateCreateTransaction.getText().toString();
        final String notification = "Your Payment of Rs" + amount + " is successful";
        final HashMap<String, String> paymentMap = new HashMap<>();
        paymentMap.put("Amount", amount);
        paymentMap.put("Comments", comment);
        paymentMap.put("Date", date);
        paymentMap.put("Notification", notification);

        String commentAdmin = "WE HAVE RECIEVED";
        final HashMap<String, String> updateAdminTransaction = new HashMap<>();
        updateAdminTransaction.put("Amount", amount);
        updateAdminTransaction.put("Comments", commentAdmin);
        updateAdminTransaction.put("UserID", ID);
        updateAdminTransaction.put("Date", date);
        updateAdminTransaction.put("PlanName", planName);

        //PlanA
        if (planName.compareToIgnoreCase("PlanA") == 0) {

            DatabaseReference databaseReference1 = firebaseDatabase.getReference().child(planName).child("UsersList").child(setName).child(userID);
            //pushKey = databaseReference1.push().getKey();
            //Log.v("Pushkey",pushKey);
            databaseReference1.child("Transactions").push().setValue(paymentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    databaseReferenceAdmin.push().setValue(updateAdminTransaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updateTransactionInfoA();
                            updateCollectorData();
                        }
                    });
                    sendSMSToUser(phone, amount, date);

                }
            });
        }
        //Plan B
        else if (planName.compareToIgnoreCase("PlanB") == 0) {
            DatabaseReference databaseReference1 = firebaseDatabase.getReference().child(planName).child("UsersList").child(userID);

            databaseReference1.child("Transactions").push().setValue(paymentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    databaseReferenceAdmin.push().setValue(updateAdminTransaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            updatePlanBdata();
                            updateCollectorData();

                        }
                    });
                }
            });

            //here it was
        }
        //Plan B
        else if (planName.compareToIgnoreCase("PlanC") == 0) {

            updatePlanCdata();
           /* DatabaseReference databaseReference1 = firebaseDatabase.getReference().child(planName).child("UsersList").child(userID);

            databaseReference1.child("Transactions").push().setValue(paymentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    databaseReferenceAdmin.push().setValue(updateAdminTransaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            //updateCollectorData();
                            // Toasty.success(getContext(), "Transaction Succesful").show();
                            // Toast.makeText(getActivity(), "Please check the number you entered",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }); */

            //here it was
        }

        //Update Transactions info in collector node
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("CollectorsInfo").child(preferences.getString("UserID", ""));
        paymentMap.put("UserID", ID);
        databaseReference1.child("Transactions").push().setValue(paymentMap);

    }

    private void updatePlanCdata() {
        Toasty.info(getContext(), "Plan C", Toasty.LENGTH_LONG).show();
    }

    public void updatePlanBdata() {


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
                            long completedMonths1 = 0;
                            if (snapshot.hasChild("GoldSaved")) {
                                Double goldSaved = snapshot.child("GoldSaved").getValue(Double.class);
                                goldSaved = goldSaved + grams;
                                databaseReference3.child("GoldSaved").setValue(goldSaved);
                            } else {
                                databaseReference3.child("GoldSaved").setValue(grams);

                            }

                            if (snapshot.hasChild("CompletedMonths")) {
                                completedMonths1 = snapshot.child("CompletedMonths").getValue(Long.class);
                                long totalAmount1 = Integer.parseInt(mBinding.userAmountCreateTransaction.getText().toString());
                                //snapshot.child("InstallmentAmount").getValue(Long.class);
                                completedMonths1 = completedMonths1 + 1;
                                databaseReference3.child("CompletedMonths").setValue(completedMonths1);

                                totalAmount1 = completedMonths1 * totalAmount1;
                                databaseReference3.child("TotalAmount").setValue(totalAmount1);
                                databaseReference3.child("TotalTransactions").setValue(completedMonths1);
                            } else {
                                Toasty.info(getContext(), "Something went wrong ", Toasty.LENGTH_LONG).show();
                            }

                            Toasty.success(getContext(), "Transaction Successfull").show();
                            dismiss();
                            //sendSMSToUser(phone, amount, date);
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

    //For A B C
    public void updateCollectorData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("CollectorsInfo").child(preferences.getString("UserID", ""));
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Count
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                String time = sdf.format(new Date());
                //String[] _time=time.split("/");
                int _dd = Integer.parseInt(time);
                long todaysDate = 0;
                long todaysAmount = 0;
                long todaysCount = 0;
                //Setting up today's date
                SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                final String planName = preferences.getString("Plan", "");
                String userKey = preferences.getString("UserKey", "");
                Log.v("UserKeyyy", userKey);

                if (snapshot.hasChild("TodaysDate"))//child("CollectorsInfo").child(ID).hasChild("TodaysDate"))
                {
                    todaysDate = snapshot.child("TodaysDate").getValue(Long.class);
                    Log.v("Err chk", String.valueOf(todaysDate));
                    // databaseReference1.child("TodaysDate").setValue(_dd);
                } else {
                    databaseReference1.child("TodaysDate").setValue(_dd);
                    todaysDate = _dd;
                    Log.v("Else", String.valueOf(todaysDate));
                }
                Log.v("DD & TD ", String.valueOf(_dd + " -" + todaysDate));
                Log.v("Uid", userID);

                if (todaysDate == _dd) {

                    if (snapshot.hasChild("TodaysCollectionCount")) {
                        todaysCount = snapshot.child("TodaysCollectionCount").getValue(Long.class);
                        Log.v("TodayCount", String.valueOf(todaysCount));
                        // todaysCount = snapshot.child("TodaysCollectionCount").getValue(Long.class);
                        databaseReference1.child("TodaysCollectionCount").setValue(todaysCount + 1);
                    } else {
                        //  todaysCount = snapshot.child("TodaysCollectionCount").getValue(Long.class);
                        databaseReference1.child("TodaysCollectionCount").setValue(1);
                    }
                    //Amount

                    if (snapshot.hasChild("TodaysCollectionAmount")) {
                        todaysAmount = snapshot.child("TodaysCollectionAmount").getValue(Long.class);
                        databaseReference1.child("TodaysCollectionAmount").setValue(todaysAmount + Integer.parseInt(mBinding.userAmountCreateTransaction.getText().toString()));
                    } else {
                        // todaysAmount = snapshot.child("TodaysCollectionAmount").getValue(Long.class);
                        databaseReference1.child("TodaysCollectionAmount").setValue(Integer.parseInt(mBinding.userAmountCreateTransaction.getText().toString()));
                    }
                } else {
                    databaseReference1.child("TodaysCollectionCount").setValue(0);
                    databaseReference1.child("TodaysCollectionAmount").setValue(0);
                    databaseReference1.child("TodaysDate").setValue(_dd);
                }
                NavigationUtilMain.INSTANCE.setUpHomeScreen();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateTransactionInfoA() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child(planName);
        final DatabaseReference databaseReference2 = firebaseDatabase.getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                long planAmount = snapshot.child("PlanAmount").getValue(long.class);
                if (snapshot.child("UsersList").child("Set1").child(userID).hasChild("CompletedMonths")) {
                    long completedMonths = snapshot.child("UsersList").child("Set1").child(userID).child("CompletedMonths").getValue(Long.class);
                    Log.v("user IDDD", userID);
                    long totalMonths = snapshot.child("UsersList").child("Set1").child(userID).child("InstallmentPeriod").getValue(Long.class);
                    if (totalMonths == completedMonths) {

                        databaseReference.child("UsersList").child("Set1").child(userID).child("PlanCompletionStatus").setValue("Completed");
                        databaseReference2.child("PlanCompletionStatus").child(mBinding.userIdCreateTransaction.getText().toString()).setValue("Completed");
                        mBinding.allowPayment.setVisibility(View.GONE);
                        mBinding.userCompleted.setVisibility(View.VISIBLE);
                        Toasty.success(getContext(), "The plan has completed", Toasty.LENGTH_LONG).show();
                    }
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
                NavigationUtilMain.INSTANCE.setUpHomeScreen();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            // Toasty.success(getContext(), "SMS sentt to:" + phone).show();
            return stringBuffer.toString();

        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            Log.v("TAG", "SMS ERROR=>" + e);
            //   Toasty.error(getContext(), e.getMessage()).show();
            return "Error " + e;
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
