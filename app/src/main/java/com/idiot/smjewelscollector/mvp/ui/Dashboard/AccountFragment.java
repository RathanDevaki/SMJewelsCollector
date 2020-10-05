package com.idiot.smjewelscollector.mvp.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentAccountBinding;
import com.idiot.smjewelscollector.databinding.FragmentHomeBinding;

import net.glxn.qrgen.android.QRCode;

public class AccountFragment extends Fragment implements DashboardContract.View {


    DashboardPresenter mPresenter;
    FragmentAccountBinding mBinding;


    public AccountFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {
        fetchInfo();
       mBinding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.signOut(getActivity());
            }
        });
    }

    private void fetchInfo() {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("CollectorsInfo").child("coll_202008001").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mBinding.collectorIdProfile.setText(snapshot.child("Collector_ID").getValue(String.class).toString());
                mBinding.collectorNameProfile.setText(snapshot.child("Name").getValue(String.class));
                Glide
                        .with(getContext())
                        .load(snapshot.child("ProfilePhoto").getValue(String.class))
                        .into(mBinding.collectorPhotoProfile);

                generateQRCode(snapshot.child("Collector_ID").getValue(String.class).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void generateQRCode(String id) {

        Bitmap myBitmap = QRCode.from(id).withSize(200,200).bitmap();
        mBinding.collectorCodeProfile.setImageBitmap(myBitmap);

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}
