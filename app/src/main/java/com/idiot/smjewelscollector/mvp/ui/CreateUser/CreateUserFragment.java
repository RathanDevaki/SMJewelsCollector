package com.idiot.smjewelscollector.mvp.ui.CreateUser;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;


import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.idiot.smjewelscollector.MainActivity;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCreateUserBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.HomeFragment;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtilMain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

import static android.app.Activity.RESULT_OK;


public class CreateUserFragment extends Fragment implements DashboardContract.View, AdapterView.OnItemSelectedListener {

    DashboardPresenter mPresenter;
    FragmentCreateUserBinding mBinding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE = 1;
    StorageReference storageReference;
    private BottomSheetBehavior sheetBehavior;

    EasyImage easyImage;

    int imageFlag = 0;


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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_user, container, false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        //Setting up of plan Spinner
        mBinding.planSpinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        String[] plans = {"PlanA", "PlanB", "PlanC"};
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, plans);
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

        //Open Calendar
        mBinding.userDOBSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendar();
            }
        });

    }

    private void openCalendar() {

        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                mBinding.userDOBSignUp.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void selectImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMAGE_CODE);

        // Pix.start((FragmentActivity) getContext(), Options.init().setRequestCode(100));

        easyImage = new EasyImage.Builder(getContext())
                .setCopyImagesToPublicGalleryFolder(true)
                .setFolderName("SM Jewels")
                .allowMultiple(false)
                .build();

        easyImage.openChooser(CreateUserFragment.this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == Activity.RESULT_OK && resultCode == requestCode) {
////
////            imageUri = data.getData();
////            mBinding.userPhotoCreateAccount.setImageURI(imageUri);
//        }

        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                // onPhotosReturned(imageFiles);

                imageFlag = 1;
                imageUri = Uri.fromFile(new File(imageFiles[0].getFile().toString()));
                Log.v("TAG", "ImageUri=>" + imageUri);
                mBinding.userPhotoCreateAccount.setImageURI(imageUri);

            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });


    }


    private void signUpUser() {

        if (TextUtils.isEmpty(mBinding.userNameSignUp.getText())) {
            mBinding.userNameSignUp.setError("Enter Name");
            Toasty.success(getContext(), "Enter Name").show();
        } else if (TextUtils.isEmpty(mBinding.userPhoneSignUp.getText()) || mBinding.userPhoneSignUp.getText().length() < 10) {
            mBinding.userPhoneSignUp.setError("Invalid Number");
            Toasty.success(getContext(), "Invalid Number").show();
        } else if (TextUtils.isEmpty(mBinding.userAddressSignUp.getText())) {
            mBinding.userAddressSignUp.setError("Enter Address");
            Toasty.success(getContext(), "Enter Address").show();
        } else if (TextUtils.isEmpty(mBinding.userPincodeSignUp.getText()) || mBinding.userPincodeSignUp.getText().length() < 6) {
            mBinding.userPincodeSignUp.setError("Invalid Pincode");
            Toasty.success(getContext(), "Invalid Pincode").show();
        } else if (TextUtils.isEmpty(mBinding.userDOBSignUp.getText())) {
            mBinding.userDOBSignUp.setError("Enter DOB");
            Toasty.success(getContext(), "Enter DOB").show();
        } else {
            uploadData();
        }

    }

    private void uploadData() {

        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("ProfilePhotos");

        if (imageFlag == 1) {
            final StorageReference reference = storageReference.child(mBinding.userIdSignup.getText().toString() + ".jpg");
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            usersMap.put("ProfilePhoto", uri.toString());

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();

                            usersMap.put("Name", mBinding.userNameSignUp.getText().toString());
                            usersMap.put("Phone", mBinding.userPhoneSignUp.getText().toString());
                            usersMap.put("EmailID", mBinding.userEmailSignUp.getText().toString());
                            usersMap.put("Address", mBinding.userAddressSignUp.getText().toString());
                            usersMap.put("PinCode", mBinding.userPincodeSignUp.getText().toString());
                            usersMap.put("DOB", mBinding.userDOBSignUp.getText().toString());
                            usersMap.put("PAN", mBinding.userPANSignUp.getText().toString());
                            usersMap.put("Aadhar", mBinding.userAadharSignUp.getText().toString());
                            usersMap.put("Nominee", mBinding.userNomineeSignUp.getText().toString());
                            usersMap.put("NomineeRelationship", mBinding.userNomineeRelationshipSignUp.getText().toString());
                            usersMap.put("NomineePhone", mBinding.userNomineePhoneSignUp.getText().toString());
                            usersMap.put("Plan", mBinding.planSpinner.getSelectedItem().toString());
                            usersMap.put("TotalAmount", 0);


                            final String pushKey = databaseReference.push().getKey();

                            final HashMap<String, String> userMap2 = new HashMap<>();
                            userMap2.put("Phone", mBinding.userPhoneSignUp.getText().toString());
                            userMap2.put("UserID", pushKey);
                            userMap2.put("PlanName", mBinding.planSpinner.getSelectedItem().toString());
                            userMap2.put("SetName", "Set1");

                            final HashMap<String, Object> userMap3 = new HashMap<>();
                            userMap3.put("TotalMonths", 18);
                            userMap3.put("CompletedMonths", 0);
                            userMap3.put("ID", Long.parseLong(mBinding.userIdSignup.getText().toString()));

                            if (mBinding.planSpinner.getSelectedItem().toString().compareToIgnoreCase("PlanA") == 0) {
                                databaseReference.child("PlanA").child("UsersList").child("Set1").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        databaseReference.child("UsersList").child(mBinding.userIdSignup.getText().toString()).setValue(userMap2);

                                        databaseReference.child("PlanA").child("UsersList").child("Set1").child(pushKey).updateChildren(userMap3);

                                        Toasty.success(getContext(), "Account Created Successfully").show();

                                        NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                    }
                                });
                            } else if (mBinding.planSpinner.getSelectedItem().toString().compareToIgnoreCase("PlanB") == 0) {

                                usersMap.put("ID", mBinding.userIdSignup.getText().toString());

                                databaseReference.child("PlanB").child("UsersList").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        databaseReference.child("UsersList").child(mBinding.userIdSignup.getText().toString()).setValue(userMap2);

                                        Toasty.success(getContext(), "Account Created Successfully").show();

                                        NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                    }
                                });
                            }


                        }
                    });
                }
            });
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            usersMap.put("Name", mBinding.userNameSignUp.getText().toString());
            usersMap.put("Phone", mBinding.userPhoneSignUp.getText().toString());
            usersMap.put("EmailID", mBinding.userEmailSignUp.getText().toString());
            usersMap.put("Address", mBinding.userAddressSignUp.getText().toString());
            usersMap.put("PinCode", mBinding.userPincodeSignUp.getText().toString());
            usersMap.put("DOB", mBinding.userDOBSignUp.getText().toString());
            usersMap.put("PAN", mBinding.userPANSignUp.getText().toString());
            usersMap.put("Aadhar", mBinding.userAadharSignUp.getText().toString());
            usersMap.put("Nominee", mBinding.userNomineeSignUp.getText().toString());
            usersMap.put("NomineeRelationship", mBinding.userNomineeRelationshipSignUp.getText().toString());
            usersMap.put("NomineePhone", mBinding.userNomineePhoneSignUp.getText().toString());
            usersMap.put("Plan", mBinding.planSpinner.getSelectedItem().toString());

            final String pushKey = databaseReference.push().getKey();

            final HashMap<String, String> userMap2 = new HashMap<>();
            userMap2.put("Phone", mBinding.userPhoneSignUp.getText().toString());
            userMap2.put("UserID", pushKey);
            userMap2.put("PlanName", "PlanA");
            userMap2.put("SetName", "Set1");

            final HashMap<String, Object> userMap3 = new HashMap<>();
            userMap3.put("TotalMonths", 18);
            userMap3.put("CompletedMonths", 0);
            userMap3.put("TotalAmount", 0);
            userMap3.put("ID", Long.parseLong(mBinding.userIdSignup.getText().toString()));


            databaseReference.child("PlanA").child("UsersList").child("Set1").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    databaseReference.child("UsersList").child(mBinding.userIdSignup.getText().toString()).setValue(userMap2);

                    databaseReference.child("PlanA").child("UsersList").child("Set1").child(pushKey).updateChildren(userMap3);

                    Toasty.success(getContext(), "Account Created Successfully").show();

                    NavigationUtilMain.INSTANCE.setUpHomeScreen();
                }
            });
        }


    }

    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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