package com.idiot.smjewelscollector.mvp.ui.Dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentScanBinding;
import com.idiot.smjewelscollector.mvp.ui.DialogFragments.CreateTransactionDialogFragment;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtil;

import es.dmoral.toasty.Toasty;


public class ScanFragment extends Fragment implements DashboardContract.View {

    DashboardPresenter mPresenter;
    FragmentScanBinding mBinding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    public ScanFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_scan, container, false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        initiliazeScanner();

    }

    private void initiliazeScanner() {

        final CodeScanner mCodeScanner = new CodeScanner(getContext(),mBinding.scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mCodeScanner.stopPreview();
                        fetchData(result.getText());
                    }
                });
            }
        });

        mCodeScanner.startPreview();

    }

    private void fetchData(final String text) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("UsersList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (text != "") {
                    if (snapshot.hasChild(text)) {
                        // Toasty.success(getContext(), "Scan User").show();
                        String userID = snapshot.child(text).child("UserID").getValue(String.class);
                        String planName = snapshot.child(text).child("PlanName").getValue(String.class);
                        String phone = snapshot.child(text).child("Phone").getValue(String.class);

                        Bundle args = new Bundle();

                        if (planName.compareToIgnoreCase("PlanA") == 0) {
                            String setName = snapshot.child(text).child("SetName").getValue(String.class);
                            args.putString("SetName", setName);
                        }

                        CreateTransactionDialogFragment dialogFragment = new CreateTransactionDialogFragment();

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                        args.putString("UserID", userID);
                        args.putString("PlanName", planName);
                        args.putString("Phone", phone);
                        args.putString("ID", text);
                        dialogFragment.setArguments(args);
                        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }

                        ft.addToBackStack(null);
                        dialogFragment.show(ft, "dialog");

                    } else {
                        Toasty.error(getContext(), "Invalid Code Scanned").show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}