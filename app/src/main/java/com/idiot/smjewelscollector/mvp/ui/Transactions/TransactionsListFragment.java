package com.idiot.smjewelscollector.mvp.ui.Transactions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fxn.pix.Options.init;


public class TransactionsListFragment extends Fragment implements DashboardContract.View {


    FragmentTransactionsListBinding mBinding;
    DashboardPresenter mPresenter;

    int collectedAmount = 0;
    ;


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

        final List<TransactionsModal> list = new ArrayList<>();
        final TransactionsAdapter adapter = new TransactionsAdapter(getContext(), R.layout.single_transaction_list, list);
        mBinding.collectionsListView.setAdapter(adapter);


        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("CollectorsInfo").child(preferences.getString("UserID", "")).child("Transactions")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mBinding.totalTransactionsCount.setText(String.valueOf(snapshot.getChildrenCount()));
                        collectedAmount = 0;
                        adapter.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            collectedAmount = collectedAmount + Integer.parseInt(snapshot.child(ds.getKey()).child("Amount").getValue(String.class));
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

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}