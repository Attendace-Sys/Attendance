package com.project.attendance.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.project.attendance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.aprilapps.easyphotopicker.MediaFile;


public class FaceImageDataAdapter extends RecyclerView.Adapter<FaceImageDataAdapter.ViewHolder> {
    private ArrayList<MediaFile> imageBitmaps;

    private Context context;

    public FaceImageDataAdapter(Context context, ArrayList<MediaFile> imageBitmaps) {
        this.context = context;
        this.imageBitmaps = imageBitmaps;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Picasso.get()
                .load(imageBitmaps.get(position).getFile())
                .fit()
                .centerCrop()
                .into(viewHolder.img);

        final int pos = position;
        viewHolder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(pos);
            }
        });

        viewHolder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final ImagePopup imagePopup = new ImagePopup(context);
                imagePopup.setBackgroundColor(Color.BLACK);  // Optional
                imagePopup.setFullScreen(true); // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
                imagePopup.setImageOnClickClose(true);  // Optional
                imagePopup.initiatePopupWithPicasso(imageBitmaps.get(pos).getFile());
                imagePopup.viewPopup();

            }
        });

    }

    public void removeItem(int position) {
        this.imageBitmaps.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    @Override
    public int getItemCount() {
        return imageBitmaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton btnClose;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
            btnClose = view.findViewById(R.id.closeButton);
        }
    }
}