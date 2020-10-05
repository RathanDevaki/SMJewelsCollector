package com.idiot.smjewelscollector.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.idiot.smjewelscollector.Modal.CollectionsModal;
import com.idiot.smjewelscollector.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CollectionsAdapter extends ArrayAdapter<CollectionsModal> {
    public CollectionsAdapter(@NonNull Context context, int resource, @NonNull List<CollectionsModal> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_collection_list_card, parent, false);
        }

        final CollectionsModal modal = getItem(position);
        TextView name = convertView.findViewById(R.id.single_collection_name);
        TextView planName = convertView.findViewById(R.id.single_collection_plan_name);
        TextView Address = convertView.findViewById(R.id.single_collection_address);
        TextView clickToCall = convertView.findViewById(R.id.clickToCall);
        CircleImageView photo= convertView.findViewById(R.id.single_collection_photo);

        name.setText(modal.getName());
        Address.setText(modal.getAddress());
        planName.setText(modal.getPlanName());
        Glide
                .with(getContext())
                .load(modal.getProfilePhoto())
                .into(photo);

        clickToCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + modal.getPhone()));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
