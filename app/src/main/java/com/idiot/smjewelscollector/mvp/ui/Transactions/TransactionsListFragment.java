package com.idiot.smjewelscollector.mvp.ui.Transactions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiot.smjewelscollector.Adapters.TransactionsAdapter;
import com.idiot.smjewelscollector.Modal.TransactionsModal;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentTransactionsListBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.fxn.pix.Options.init;

public class TransactionsListFragment extends Fragment implements DashboardContract.View {


    FragmentTransactionsListBinding mBinding;
    DashboardPresenter mPresenter;

    int collectedAmount = 0;
    String ID;

    public TransactionsListFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transactions_list, container, false);
        mPresenter = new DashboardPresenter(this);
        initialize();
        return mBinding.getRoot();
    }

    private void initialize() {
//        ID = getArguments().getString("ID");
        final List<TransactionsModal> list = new ArrayList<>();
        final TransactionsAdapter adapter = new TransactionsAdapter(getContext(), R.layout.single_transaction_list, list);
        mBinding.collectionsListView.setAdapter(adapter);


        final SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("CollectorsInfo").child(preferences.getString("UserID", "")).child("Transactions")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mBinding.totalTransactionsCount.setText(String.valueOf(snapshot.getChildrenCount()));
                        collectedAmount = 0;
                        adapter.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String collectedAmountStr = "";
                            collectedAmountStr = snapshot.child(ds.getKey()).child("Amount").getValue(String.class);
                            collectedAmount += Integer.parseInt(collectedAmountStr);
                            Log.v("Coll Amt", String.valueOf(collectedAmount));
                            TransactionsModal modal = ds.getValue(TransactionsModal.class);
                            list.add(modal);
                        }

                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
                        mBinding.totalCollectedAmount.setText("Rs." + String.valueOf(collectedAmount) + "/-");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        final DatabaseReference databaseReference1 = firebaseDatabase.getReference().child("CollectorsInfo").child(preferences.getString("UserID", ""));
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                String time = sdf.format(new Date());
                //String[] _time=time.split("/");
                int _dd = Integer.parseInt(time);
                long todaysDate = 0;
                long todaysAmount = 0;
                long todaysCount = 0;

                //Setting up today's date
                SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                // final String planName = preferences.getString("Plan","");
                String userKey = preferences.getString("UserKey", "");
                Log.v("UserKeyyy", userKey);

                if (snapshot.hasChild("TodaysDate"))//child("CollectorsInfo").child(ID).hasChild("TodaysDate"))
                {
                    todaysDate = snapshot.child("TodaysDate").getValue(Long.class);
                    Log.v("Has todays date", String.valueOf(todaysDate));
                } else {
                    databaseReference1.child("TodaysDate").setValue(_dd);
                    todaysDate = _dd;
                    Log.v("Else ", String.valueOf(todaysDate));
                }

                if (todaysDate == _dd) {
                    Log.v("if date  ", String.valueOf(todaysDate + " ->" + _dd));
                    todaysCount = snapshot.child("TodaysCollectionCount").getValue(Long.class);
                    todaysAmount = snapshot.child("TodaysCollectionAmount").getValue(Long.class);

                    mBinding.todayCount.setText(String.valueOf(todaysCount));
                    mBinding.todayAmount.setText(String.valueOf(todaysAmount));
                } else {
                    Log.v("Else date", String.valueOf(todaysDate + " ->" + _dd));
                    databaseReference1.child("TodaysCollectionCount").setValue(0);
                    databaseReference1.child("TodaysCollectionAmount").setValue(0);
                    databaseReference1.child("TodaysDate").setValue(_dd);
                }
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
}