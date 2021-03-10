package com.idiot.smjewelscollector.mvp.ui.CreateUser;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCreateUserBinding;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardContract;
import com.idiot.smjewelscollector.mvp.ui.Dashboard.DashboardPresenter;
import com.idiot.smjewelscollector.mvp.utils.NavigationUtilMain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

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
    List<String> setList;
    EasyImage easyImage;
    //  PlanSetAdapter adapter;
    int imageFlag = 0;
    final List<String> setCount = new ArrayList<>();
    int count = 0;

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
        loadSetNames();
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
        //SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //creating pcs

        if (TextUtils.isEmpty(mBinding.userNameSignUp.getText())) {
            mBinding.userNameSignUp.setError("Enter Name");
            Toasty.error(getContext(), "Enter Name").show();
        } else if (TextUtils.isEmpty(mBinding.userPhoneSignUp.getText()) || mBinding.userPhoneSignUp.getText().length() < 10) {
            mBinding.userPhoneSignUp.setError("Invalid Number");
            Toasty.error(getContext(), "Invalid Number").show();
        } else if (TextUtils.isEmpty(mBinding.userAddressSignUp.getText())) {
            mBinding.userAddressSignUp.setError("Enter Address");
            Toasty.error(getContext(), "Enter Address").show();
        } else if (TextUtils.isEmpty(mBinding.userPincodeSignUp.getText()) || mBinding.userPincodeSignUp.getText().length() < 6) {
            mBinding.userPincodeSignUp.setError("Invalid Pincode");
            Toasty.error(getContext(), "Invalid Pincode").show();
        } else if (TextUtils.isEmpty(mBinding.userDOBSignUp.getText())) {
            mBinding.userDOBSignUp.setError("Enter DOB");
            Toasty.error(getContext(), "Enter DOB").show();
        } else {
            uploadData();
        }
    }

    private String getCurrentTimeAndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, HH:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Log.v("daten time", currentDateandTime);
        return currentDateandTime;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Log.v("date", currentDateandTime);
        return currentDateandTime;
    }

    //Count of users
    public void loadSetNames() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        setList = new ArrayList<>();
        databaseReference.child("PlanA").child("UsersList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setList.clear();
                //  adapter.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    setList.add(ds.getKey());
                    setCount.add(String.valueOf(snapshot.child(ds.getKey()).getChildrenCount()));

                    // adapter.notifyDataSetChanged();
                }
                //  mBinding.planSetListView.setAdapter(adapter);
                count = Integer.parseInt(setCount.get(0));
                Log.v("SetCount", setCount.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void uploadData() {
        mBinding.signUpBtn.setVisibility(View.GONE);
        mBinding.progressBar.setVisibility(View.VISIBLE);
        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("ProfilePhotos");
        Log.v("in upload", "yes");
        if (imageFlag == 1) {
            Log.v("in imageFlag", "yes");
            final StorageReference reference = storageReference.child(mBinding.userIdSignup.getText().toString() + ".jpg");
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String currentMonth = getCurrentDate();
                            Log.v("DateFire", currentMonth);
                            String[] lastPaidMonth = currentMonth.split("-");
                            String _day = lastPaidMonth[0];
                            String _month = lastPaidMonth[1];
                            String _year = lastPaidMonth[2];
                            //  Log.v("Seperated date",_day+""+_month+""+_year);
                            int intdate = 12;
                            int intmonth = Integer.parseInt(_month);
                            int intyear = Integer.parseInt(_year);
                            String filteredMonth = intdate + "/" + intmonth + "/" + intyear;

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();

                            //Its common for all users

                            usersMap.put("ProfilePhoto", uri.toString());
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
                            usersMap.put("LastPaidMonth", filteredMonth);
                            usersMap.put("ID", mBinding.userIdSignup.getText().toString());
                            usersMap.put("TotalAmount", 0);
                            usersMap.put("TotalTransactions", 0);
                            usersMap.put("CompletedMonths", 0);

                            final String pushKey = databaseReference.push().getKey();

                            //
                            final HashMap<String, String> userMap2 = new HashMap<>();
                            userMap2.put("Phone", mBinding.userPhoneSignUp.getText().toString());
                            userMap2.put("UserID", pushKey);
                            userMap2.put("PlanName", mBinding.planSpinner.getSelectedItem().toString());
                            userMap2.put("SetName", "Set1");


                            final HashMap<String, Object> userMap3 = new HashMap<>();
                            userMap3.put("TotalMonths", 18);
                            userMap3.put("ID", Long.parseLong(mBinding.userIdSignup.getText().toString()));

                            if (mBinding.planSpinner.getSelectedItem().toString().compareToIgnoreCase("PlanA") == 0) {
                                // Log.v("In Plan A","yes");
                                usersMap.put("InstallmentPeriod", 18);
                                usersMap.put("InstallmentAmount", 500);
                                usersMap.put("PlanCompletionStatus", "Incomplete");
                                databaseReference.child("PlanA").child("UsersList").child("Set1").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.v("present pushkey", pushKey);
                                        databaseReference.child("UsersList").child(mBinding.userIdSignup.getText().toString()).setValue(userMap2);

                                        databaseReference.child("PlanA").child("UsersList").child("Set1").child(pushKey).updateChildren(userMap3);

                                    }
                                });
                                //Upload PCS
                                final DatabaseReference databaseReference1 = firebaseDatabase.getReference(); //.child("CollectorsInfo").child(preferences.getString("UserID", ""));
                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.v("PCS", "yes");
                                        if (snapshot.hasChild("PlanCompletionStatus")) {
                                            Log.v("PCS if", "yes");
                                            databaseReference1.child("PlanCompletionStatus").child(mBinding.userIdSignup.getText().toString()).setValue("Incomplete");
                                            Toasty.success(getContext(), "Account Created Successfully").show();
                                            NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                        } else {
                                            Log.v("PCS else", "yes");
                                            databaseReference1.child("PlanCompletionStatus").child(mBinding.userIdSignup.getText().toString()).setValue("Incomplete");
                                            Toasty.success(getContext(), "Account Created Successfully").show();
                                            NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            } else if (mBinding.planSpinner.getSelectedItem().toString().compareToIgnoreCase("PlanB") == 0) {
                                Log.v("In Plan B", "yes");
                                usersMap.put("InstallmentPeriod", 0);
                                usersMap.put("InstallmentAmount", 0);
                                usersMap.put("PlanCompletionStatus", "NA");
                                databaseReference.child("PlanB").child("UsersList").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.v("onSuccess B", "yes");
                                        databaseReference.child("UsersList").child(mBinding.userIdSignup.getText().toString()).setValue(userMap2);

                                       /* Toasty.success(getContext(), "Account Created Successfully In Plan-B").show();
                                        Log.v("Pushkey B",pushKey);
                                        NavigationUtilMain.INSTANCE.setUpHomeScreen();*/
                                    }
                                });

                                final DatabaseReference databaseReference1 = firebaseDatabase.getReference(); //.child("CollectorsInfo").child(preferences.getString("UserID", ""));
                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.v("PCS", "yes");
                                        if (snapshot.hasChild("PlanCompletionStatus")) {
                                            Log.v("PCS if", "yes");
                                            databaseReference1.child("PlanCompletionStatus").child(mBinding.userIdSignup.getText().toString()).setValue("NA");
                                            Toasty.success(getContext(), "Account Created Successfully").show();
                                            NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                            //   Toasty.info(getContext(),"has PCS",Toasty.LENGTH_LONG).show();
                                        } else {
                                            Log.v("PCS else", "yes");
                                            databaseReference1.child("PlanCompletionStatus").child(mBinding.userIdSignup.getText().toString()).setValue("NA");
                                            Toasty.success(getContext(), "Account Created Successfully").show();
                                            NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                            Log.v("PCS else done PlanB", "yes");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            } else if (mBinding.planSpinner.getSelectedItem().toString().compareToIgnoreCase("PlanC") == 0) {
                                Log.v("In Plan C", "yes");
                                usersMap.put("InstallmentPeriod", 12);
                                usersMap.put("InstallmentAmount", 0);
                                usersMap.put("PlanCompletionStatus", "NA");
                                databaseReference.child("PlanC").child("UsersList").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.v("onSuccess c", "yes");
                                        databaseReference.child("UsersList").child(mBinding.userIdSignup.getText().toString()).setValue(userMap2);
                                    }
                                });
                                final DatabaseReference databaseReference1 = firebaseDatabase.getReference(); //.child("CollectorsInfo").child(preferences.getString("UserID", ""));
                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.v("PCS", "yes");
                                        if (snapshot.hasChild("PlanCompletionStatus")) {
                                            Log.v("PCS if", "yes");
                                            databaseReference1.child("PlanCompletionStatus").child(mBinding.userIdSignup.getText().toString()).setValue("NA");
                                            Toasty.success(getContext(), "Account Created Successfully").show();
                                            NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                            // Toasty.info(getContext(),"has PCS",Toasty.LENGTH_LONG).show();
                                        } else {
                                            Log.v("PCS else", "yes");
                                            databaseReference1.child("PlanCompletionStatus").child(mBinding.userIdSignup.getText().toString()).setValue("NA");
                                            Toasty.success(getContext(), "Account Created Successfully").show();
                                            NavigationUtilMain.INSTANCE.setUpHomeScreen();
                                            Log.v("PCS else done PlanC", "yes");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        }
                    });
                }
            });
        } else {

            Toasty.error(getContext(), "You haven't choose profile photo").show();
            mBinding.progressBar.setVisibility(View.GONE);
            mBinding.signUpBtn.setVisibility(View.VISIBLE);
  
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