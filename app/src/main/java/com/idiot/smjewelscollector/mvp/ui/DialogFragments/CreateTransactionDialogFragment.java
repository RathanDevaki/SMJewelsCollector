package com.idiot.smjewelscollector.mvp.ui.DialogFragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCreateTransactionDialogBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;


public class CreateTransactionDialogFragment extends DialogFragment implements DashboardContract.View {

    FragmentCreateTransactionDialogBinding mBinding;
    DashboardPresenter mPresenter;


    public CreateTransactionDialogFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_transaction_dialog,container,false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {



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
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}