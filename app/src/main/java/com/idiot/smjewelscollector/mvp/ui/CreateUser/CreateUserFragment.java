package com.idiot.smjewelscollector.mvp.ui.CreateUser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCreateUserBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;


public class CreateUserFragment extends Fragment implements DashboardContract.View, AdapterView.OnItemSelectedListener {

    DashboardPresenter mPresenter;
    FragmentCreateUserBinding mBinding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String pushKey,user_id,phone,plan;
    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE=1;
    StorageReference storageReference;
    private BottomSheetBehavior sheetBehavior;



    public CreateUserFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_user, container, false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        //Setting up of plan Spinner
        mBinding.planSpinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        String[] plans = {"PlanA", "PlanB","PlanC"};
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,plans);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        mBinding.planSpinner.setAdapter(aa);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersList");

        mBinding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        mBinding.userPhotoCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() !=null) {
            imageUri = data.getData();
            mBinding.userPhotoCreateAccount.setImageURI(imageUri);
        }

    }


    private void signUpUser() {

        if (TextUtils.isEmpty(mBinding.userNameSignUp.getText())){
            mBinding.userNameSignUp.setError("Enter Name");
            Toasty.success(getContext(),"Enter Name").show();
        } else if (TextUtils.isEmpty(mBinding.userPhoneSignUp.getText()) || mBinding.userPhoneSignUp.getText().length() < 10){
            mBinding.userPhoneSignUp.setError("Invalid Number");
            Toasty.success(getContext(),"Invalid Number").show();
        }else if (TextUtils.isEmpty(mBinding.userPANSignUp.getText()) || mBinding.userPANSignUp.getText().length() < 10){
            mBinding.userPANSignUp.setError("Invalid Pan Number");
            Toasty.success(getContext(),"Invalid Pan Number").show();
        }else if (TextUtils.isEmpty(mBinding.userAadharSignUp.getText()) || mBinding.userAadharSignUp.getText().length() < 12){
            mBinding.userAadharSignUp.setError("Invalid Aadhar Number");
            Toasty.success(getContext(),"Invalid Aadhar Number").show();
        } else if (TextUtils.isEmpty(mBinding.userEmailSignUp.getText())){
            mBinding.userEmailSignUp.setError("Enter Email");
            Toasty.success(getContext(),"Enter Email").show();
        }else if (TextUtils.isEmpty(mBinding.userAddressSignUp.getText())){
            mBinding.userAddressSignUp.setError("Enter Address");
            Toasty.success(getContext(),"Enter Address").show();
        } else if (TextUtils.isEmpty(mBinding.userPincodeSignUp.getText()) || mBinding.userPincodeSignUp.getText().length() < 6){
            mBinding.userPincodeSignUp.setError("Invalid Pincode");
            Toasty.success(getContext(),"Invalid Pincode").show();
        }else if (TextUtils.isEmpty(mBinding.userDOBSignUp.getText())){
            mBinding.userDOBSignUp.setError("Enter DOB");
            Toasty.success(getContext(),"Enter DOB").show();
        } else {
            uploadData();
        }
    }
    private void uploadData()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("PlanA").child("UsersList");
        HashMap<String,String> usersMap = new HashMap<>();
        usersMap.put("ID",mBinding.userIdSignup.getText().toString());
        usersMap.put("Name",mBinding.userNameSignUp.getText().toString());
        usersMap.put("Phone",mBinding.userPhoneSignUp.getText().toString());
        usersMap.put("EmailID",mBinding.userEmailSignUp.getText().toString());
        usersMap.put("Address",mBinding.userAddressSignUp.getText().toString());
        usersMap.put("PinCode",mBinding.userPincodeSignUp.getText().toString());
        usersMap.put("DOB",mBinding.userDOBSignUp.getText().toString());
        usersMap.put("PAN",mBinding.userPANSignUp.getText().toString());
        usersMap.put("Aadhar",mBinding.userAadharSignUp.getText().toString());
        usersMap.put("Nominee",mBinding.userNomineeSignUp.getText().toString());
        usersMap.put("NomineeRelationship",mBinding.userNomineeRelationshipSignUp.getText().toString());
        usersMap.put("NomineePhone",mBinding.userNomineePhoneSignUp.getText().toString());
        usersMap.put("Plan",mBinding.planSpinner.getSelectedItem().toString());

        //total mnth=18,compl mth=0,tot amt=0;
       user_id=mBinding.userIdSignup.getText().toString();
       phone=mBinding.userPhoneSignUp.getText().toString();
       plan=mBinding.planSpinner.getSelectedItem().toString();
       pushKey= databaseReference.push().getKey();
        Log.v("log",pushKey);

       // databaseReference.setValue(usersMap);

       databaseReference.child("Set1").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                uploadUsersList(pushKey,phone,plan,user_id);
                Toasty.success(getContext(),"Account Created Successfully").show();
            }
        });
    }

    private void uploadUsersList(String pushKey, String phone, String plan,String user_id)
    {
        FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferences=firebasedatabase.getReference().child("UsersList");
        HashMap<String,String>usersListMap=new HashMap<>();
        usersListMap.put("Phone",phone);
        usersListMap.put("Plan",plan);
        usersListMap.put("UserID",pushKey);
        usersListMap.put("SetName","Set1");
        //databaseReferences.child(user_id).setValue(usersListMap);
       databaseReferences.child(user_id).setValue(usersListMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"User added",Toast.LENGTH_LONG).show();
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}