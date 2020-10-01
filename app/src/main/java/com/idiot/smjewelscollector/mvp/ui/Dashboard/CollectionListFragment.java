package com.idiot.smjewelscollector.mvp.ui.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        List<CollectionsModal> list = new ArrayList<>();
        final CollectionsAdapter adapter = new CollectionsAdapter(getContext(),R.layout.single_collection_list_card,list);
        mBinding.collectionListGrid.setAdapter(adapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("CollectorsInfo").child("coll_202008001");
        final DatabaseReference databaseReference1 = firebaseDatabase.getReference();
        databaseReference.child("AssignedUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    String key = snapshot1.getKey();
                    final String value = snapshot1.getValue(String.class);

                    databaseReference1.child(value).child("UsersList").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CollectionsModal modal = snapshot.getValue(CollectionsModal.class);
                            modal.setPlanName(value);
                            adapter.add(modal);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void pushDummyData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("CollectorsInfo").child("coll_202008001");
        databaseReference.child("AssignedUsers").child("-MEDR8d8JeyY-6EtvLPZ").setValue("PlanA");
        databaseReference.child("AssignedUsers").child("-MEDRFYQdXd--r5HnKzw").setValue("PlanA");
        databaseReference.child("AssignedUsers").child("-MEDRHch_dwJoF8u4-u6").setValue("PlanA");
        databaseReference.child("AssignedUsers").child("-MEDRJN8VF-pSmyE6ew6").setValue("PlanA");

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}