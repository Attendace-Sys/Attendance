package com.project.attendance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.FaceDetector;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.project.attendance.Adapter.FaceImageDataAdapter;
import com.project.attendance.Adapter.ImagePopDataAdapter;
import com.project.attendance.Networking.FaceRect;

import org.bytedeco.javacpp.presets.opencv_core;

import java.util.ArrayList;

public class ImagePopActivity extends Activity {

    TextView txtClose;
    ArrayList<Bitmap> listDrawBitmap;
    private ImagePopDataAdapter imagesAdapter;
    protected RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

//        getWindow().setLayout((int)(width), (int) (height*.8));
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.x = 0;
//        params.y = -20;

//        getWindow().setAttributes(params);

        txtClose = (TextView) findViewById(R.id.txtclose);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        listDrawBitmap = new ArrayList<Bitmap>();
        int index = 0;
        for (Bitmap bitmap: Global.listBitmap)
        {
            ArrayList<FaceRect> listRect = Global.jsonResponseForUploadedImages.get(index).getFaceRect();
            Bitmap bm = bitmap;
            bm = drawBitmapWithRetangle(getApplicationContext(), bitmap, listRect);
            listDrawBitmap.add(bm);
            index++;
        }

        imagesAdapter = new ImagePopDataAdapter(this, listDrawBitmap);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayout);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagesAdapter);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Bitmap drawBitmapWithRetangle(Context context, Bitmap b, ArrayList<FaceRect> faceRects) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bmOverlay = Bitmap.createBitmap(b.getWidth(), b.getHeight(), b.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);


        Paint paintText = new Paint();
//        paintText.setColor(Color.BLUE);
        paintText.setStyle(Paint.Style.FILL);


        canvas.drawBitmap(b, 0, 0, null);

        for (FaceRect face: faceRects) {
            Rect rect = new Rect();
            rect.left = Math.toIntExact(face.getLeft());
            rect.top = Math.toIntExact(face.getTop());
            rect.right = Math.toIntExact(face.getRight());
            rect.bottom = Math.toIntExact(face.getBottom());
            Double score = face.getScore();
            if(score <= 0.04)
            {
                paint.setColor(Color.GREEN);
                paintText.setColor(Color.GREEN);
            } else if ((score > 0.04) && (score < 0.06))
            {
                paint.setColor(Color.YELLOW);
                paintText.setColor(Color.YELLOW);
            } else if (score >= 0.06)
            {
                paint.setColor(Color.RED);
                paintText.setColor(Color.RED);
            }
            int height = rect.bottom - rect.top;
            int weight = rect.right - rect.left;
            paint.setStrokeWidth(height/20);
            paintText.setTextSize(height/5);
            canvas.drawRect(rect, paint);
            String fScore = String.format("%.3f", face.getScore());
            canvas.drawText(face.getName(),face.getLeft(), face.getTop() - weight/7, paintText);
            canvas.drawText(face.getStudentId() + " - " + fScore,face.getLeft(), face.getTop() - weight/7 - paintText.descent() + paintText.ascent(), paintText);

        }
        return bmOverlay;
    }
}
