package com.project.attendance.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.project.attendance.Global;
import com.project.attendance.Networking.JsonResponseForUploadedImage;
import com.project.attendance.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        convertToFile(imageBitmaps.get(position), Global.imageNames.get(position));

    }

    private static File convertToFile(Bitmap bitmap, String name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/checking_image");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = name + "_" + timeStamp + ".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public int getItemCount() {
        return imageBitmaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        //TextView txtNameImage;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
            //txtNameImage = view.findViewById(R.id.txt_image_name);
        }
    }
}