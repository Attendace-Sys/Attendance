package com.project.attendance.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.project.attendance.Networking.JsonResponseForUploadedImage;
import com.project.attendance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import pl.aprilapps.easyphotopicker.MediaFile;

public class ImagePopDataAdapter  extends RecyclerView.Adapter<ImagePopDataAdapter.ViewHolder> {
    private ArrayList<Bitmap> imageBitmaps;

    private Context context;

    public ImagePopDataAdapter(Context context, ArrayList<Bitmap> imageBitmaps) {
        this.context = context;
        this.imageBitmaps = imageBitmaps;
    }

    @Override
    public ImagePopDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item_pop, viewGroup, false);
        return new ImagePopDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImagePopDataAdapter.ViewHolder viewHolder, int position) {

        viewHolder.img.setImageBitmap(imageBitmaps.get(position));


    }

    @Override
    public int getItemCount() {
        return imageBitmaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
        }
    }
}