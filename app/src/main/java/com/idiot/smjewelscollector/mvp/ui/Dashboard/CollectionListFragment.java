package com.idiot.smjewelscollector.mvp.ui.Dashboard;

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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiot.smjewelscollector.Adapters.CollectionsAdapter;
import com.idiot.smjewelscollector.Modal.CollectionsModal;
import com.idiot.smjewelscollector.R;
import com.idiot.smjewelscollector.databinding.FragmentCollectionListBinding;

import java.util.ArrayList;
import java.util.List;


public class CollectionListFragment extends Fragment implements DashboardContract.View {

    FragmentCollectionListBinding mBinding;
    DashboardPresenter mPresenter;



    public CollectionListFragment() {
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
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_collection_list,container,false);
        mPresenter = new DashboardPresenter(this);
        init();
        return mBinding.getRoot();
    }

    private void init() {

        //Initialize Ad
        AdRequest adRequest = new AdRequest.Builder().build();
        mBinding.collectionListAdBanner.loadAd(adRequest);
        //pushDummyData();

        fetchData();


    }

    private void fetchData() {
        //pushDummyData();
        Log.v("yes","fetchdata");
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String userKey = preferences.getString("UserID", "");
        List<CollectionsModal> list = new ArrayList<>();
        final CollectionsAdapter adapter = new CollectionsAdapter(getContext(), R.layout.single_collection_list_card, list);
        mBinding.collectionListGrid.setAdapter(adapter);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("CollectorsInfo");//.child("coll_202008001");
        //



        databaseReference.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                if(snapshot.hasChild("AssignedUsers"))
                {
                    Log.v("yes","assigned");
                    Log.v("fhfhdhhd", userKey);
                    for (DataSnapshot snapshot1 : snapshot.child("AssignedUsers").getChildren()) {
                        final String key = snapshot1.getKey();
                        final String value = snapshot1.getValue(String.class);
                        // Log.v("val",value);
                        //Log.v("Key ",key);
                        if (value.compareToIgnoreCase("PlanA") == 0){
                            databaseReference1.child(value).child("UsersList").child("Set1").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    Log.v("Key ", key);
                                    Log.v("val", value);
                                    if (snapshot2.hasChild("Address")) {
                                        Log.v("Key ", "kaiaka");
                                    }
                                    CollectionsModal modal = snapshot2.getValue(CollectionsModal.class);
                                    modal.setPlanName(value);
                                    modal.setAddress(snapshot2.child("Address").getValue(String.class));
                                    if (!snapshot2.hasChild("ProfilePhoto")) {
                                        Log.v("j", "mm");
                                        modal.setProfilePhoto("https://firebasestorage.googleapis.com/v0/b/sm-jewels.appspot.com/o/img_162044.png?alt=media&token=c5445416-61d0-4ea7-90e7-a77a5e65cd09");
                                    }

                                    adapter.add(modal);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    }
                        else{
                            databaseReference1.child(value).child("UsersList").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    Log.v("Key ", key);
                                    Log.v("val", value);
                                    if (snapshot2.hasChild("Address")) {
                                        Log.v("In planB or C ", "Yes Bro");
                                    }
                                    CollectionsModal modal = snapshot2.getValue(CollectionsModal.class);
                                    modal.setPlanName(value);
                                    modal.setAddress(snapshot2.child("Address").getValue(String.class));
                                    if (!snapshot2.hasChild("ProfilePhoto")) {
                                        Log.v("j", "mm");
                                        modal.setProfilePhoto("https://firebasestorage.googleapis.com/v0/b/sm-jewels.appspot.com/o/img_162044.png?alt=media&token=c5445416-61d0-4ea7-90e7-a77a5e65cd09");
                                    }

                                    adapter.add(modal);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                }
                }
                else
                {
                    mBinding.collectionListGrid.setVisibility(View.GONE);
                    mBinding.noCustomerText.setVisibility(View.VISIBLE);
                  //  Toasty.error(getContext(),"No assigned users found",Toasty.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

  /*  private void pushDummyData() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("CollectorsInfo").child("20200800001");
        databaseReference.child("Name").child("Node").child("dummy").setValue("Load");
       /* databaseReference.child("AssignedUsers").child("-MEDRFYQdXd--r5HnKzw").setValue("PlanA");
        databaseReference.child("AssignedUsers").child("-MEDRHch_dwJoF8u4-u6").setValue("PlanA");
        databaseReference.child("AssignedUsers").child("-MEDRJN8VF-pSmyE6ew6").setValue("PlanA");

    }*/

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}