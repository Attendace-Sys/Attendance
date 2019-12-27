package com.project.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.project.attendance.Adapter.FaceImageDataAdapter;
import com.project.attendance.Adapter.GridSpacingItemDecoration;
import com.project.attendance.Adapter.ResultChekingAttendanceAdapter;
import com.project.attendance.Model.Attendance;
import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;
import com.project.attendance.Networking.Attend;
import com.project.attendance.Networking.Result;
import com.project.attendance.Networking.Recognitions;
import com.project.attendance.Utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakingPictureAttendanceActivity extends AppCompatActivity {

    private static final String PHOTOS_KEY = "taking_picture_attendance_photos_list";
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;

    protected RecyclerView recyclerView;
    protected Button cameraGalleryBtn;
    protected Button sendBtn;

    String scheduleCode;
    private FaceImageDataAdapter imagesAdapter;

    private ArrayList<MediaFile> photos = new ArrayList<>();

    private EasyImage easyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_picture_attendance);

        recyclerView = findViewById(R.id.recyclerView);
        cameraGalleryBtn = findViewById(R.id.cameraGalleryBtn);
        sendBtn = findViewById(R.id.send);

        if (savedInstanceState != null) {
            photos = savedInstanceState.getParcelableArrayList(PHOTOS_KEY);
        }


        int spanCount = 3; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;

        imagesAdapter = new FaceImageDataAdapter(this, photos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagesAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        Intent intent = getIntent();
        scheduleCode = intent.getStringExtra("scheduleCode");

        easyImage = new EasyImage.Builder(this)
                .setChooserTitle("Pick media")
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build();

        checkGalleryAppAvailability();

        cameraGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openChooser(TakingPictureAttendanceActivity.this);
                } else {
                    requestPermissionsCompat(necessaryPermissions, CHOOSER_PERMISSIONS_REQUEST_CODE);
                }
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = " Đang tiến hành \n  Vui lòng chờ...";
                Utils.showLoadingIndicator(TakingPictureAttendanceActivity.this, message);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleAutomaticCheckingAttendance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });


    }

    ArrayList<Bitmap> listClassRoomImages = new ArrayList<>();

 void handleAutomaticCheckingAttendance() {

        //first get bitmap
        for (MediaFile file : photos) {
            Bitmap bitmap = getRightOrientationBitmap(file.getFile());
            if (bitmap != null) {
                listClassRoomImages.add(bitmap);
            }
        }

        //build json

        String jsonToServer = "[";
     ArrayList<Bitmap> listImageSentToServer = new ArrayList<>();
     ArrayList<String> listImageNameSentToServer = new ArrayList<>();


        for (int index = 0; index < listClassRoomImages.size(); index++) {
            String image_name = "classroom_image_" + index;
            String jsonFacesForEachImage = extractJsonFaceInfoOnImage(listClassRoomImages.get(index));
            if (jsonFacesForEachImage.length() > 0) {

                listImageSentToServer.add(listClassRoomImages.get(index));
                listImageNameSentToServer.add(image_name);

                String joinedJson = "{ \"img_name\":" + "\"" + image_name + "\","
                        + "\"face_rect\":" + jsonFacesForEachImage + "}";

                jsonToServer += joinedJson;

                jsonToServer += ",";

            }

        }

        jsonToServer = jsonToServer.substring(0, jsonToServer.length() - 1);
        if (jsonToServer.trim().length() == 0)
            jsonToServer = "[]";
        else {
            jsonToServer += "]";
        }

        Log.d("json", jsonToServer);


        sendDataToServer(scheduleCode, listImageSentToServer, listImageNameSentToServer, jsonToServer);


     runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.hideLoadingIndicator();
            }
        });

    }


    private String rectToJson(Rect rect) {

        return "{ \"top\":" + rect.top + ','
                + "\"left\":" + rect.left + ","
                + "\"right\":" + rect.right + ","
                + "\"bottom\":" + rect.bottom + "}";
    }


    private String extractJsonFaceInfoOnImage(Bitmap bitmap) {

        Bitmap frame = bitmap;
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(frame);

        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .build();
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        ArrayList<Rect> listFaceRect = new ArrayList<>();

        Task<List<FirebaseVisionFace>> detectionTask = detector.detectInImage(firebaseVisionImage);

        try {
            // Block on a task and get the result synchronously. This is generally done
            // when executing a task inside a separately managed background thread. Doing this
            // on the main (UI) thread can cause your application to become unresponsive.
            List<FirebaseVisionFace> faces = Tasks.await(detectionTask);

            for (FirebaseVisionFace face : faces) {
                Rect bounds = face.getBoundingBox();
                listFaceRect.add(bounds);

            }

        } catch (ExecutionException e) {
            // The Task failed, this is the same exception you'd get in a non-blocking
            // failure handler.
            // ...
        } catch (InterruptedException e) {
            // An interrupt occurred while waiting for the task to complete.
            // ...
        }

        String json = "";
        if (listFaceRect.size() > 0) {
            json = "[";

            for (int i = 0; i < listFaceRect.size(); i++) {
                String rectJson = rectToJson(listFaceRect.get(i));
                json += rectJson;

                if (i < listFaceRect.size() - 1)
                    json += ", ";
            }

            json += "]";
        }

        return json;
    }


    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }
    public static Bitmap getRightOrientationBitmap(File imageFile) {
        try {
            Bitmap sourceBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());


            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);

            Matrix matrix = new Matrix();
            if (rotation != 0) {matrix.preRotate(rotationInDegrees);}
            int mBitW = Integer.parseInt(String.valueOf(sourceBitmap.getWidth()));
            int mBitH = Integer.parseInt(String.valueOf(sourceBitmap.getHeight()));

            Bitmap rotatedBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, mBitW, mBitH, matrix, true);
            return rotatedBitmap;

        } catch (Exception e) {

        }

        return null;

    }


    private Bitmap drawBitmapWithRetangle(Bitmap b, Rect rect) {
        Bitmap bmOverlay = Bitmap.createBitmap(b.getWidth(), b.getHeight(), b.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawBitmap(b, 0, 0, null);
        return bmOverlay;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PHOTOS_KEY, photos);
    }

    private void checkGalleryAppAvailability() {
        if (!easyImage.canDeviceHandleGallery()) {
            //Device has no app that handles gallery intent
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooser(TakingPictureAttendanceActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                for (MediaFile imageFile : imageFiles) {
                    Log.d("EasyImage", "Image file returned: " + imageFile.getFile().toString());
                }
                onPhotosReturned(imageFiles);
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

    private void onPhotosReturned(@NonNull MediaFile[] returnedPhotos) {
        photos.addAll(Arrays.asList(returnedPhotos));
        imagesAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(photos.size() - 1);

    }

    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;

        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(TakingPictureAttendanceActivity.this, permissions, requestCode);
    }

    private static File convertToFile(Bitmap bitmap, String name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
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

    private void sendDataToServer(String schedule_code, ArrayList<Bitmap> listToSend, ArrayList<String> imageNames, String jsonData) {

        Log.e("listToSend" ,"----" + listToSend.size());


        List<MultipartBody.Part> imgParts = new ArrayList<>();

        for (int index = 0; index < listToSend.size(); index++) {
            String fileName = imageNames.get(index);
            File file = convertToFile(listToSend.get(index), fileName);
            RequestBody requestImg = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", fileName  + ".jpg", requestImg);
            imgParts.add(part);
        }

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), schedule_code);
        RequestBody json = RequestBody.create(MediaType.parse("text/plain"), jsonData);

        Log.e("parts" ,"----" + imgParts.size());

        Log.e("json" ,"----" + jsonData);


        Call<Recognitions> call = getResponse.uploadCheckAttendance(Global.token, idBody,json, imgParts);
        call.enqueue(new Callback<Recognitions>() {
            @Override
            public void onResponse(Call<Recognitions> call, Response<Recognitions> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Utils.hideLoadingIndicator();
                        Toast.makeText(getApplicationContext(), "Gửi thành công!", Toast.LENGTH_SHORT).show();

                        Recognitions recognitions = (Recognitions) response.body();

                        ArrayList<Result> listResult = recognitions.getListStudent();


//                        Intent intent = new Intent(TakingPictureAttendanceActivity.this, DetailAttendanceActivity.class);
//
//                        intent.putExtra("scheduleCode", scheduleCode);
////                        intent.putExtra("listResult", listResult);
//                        Global.listResult = listResult;
//                        Global.nowScheduleCode = scheduleCode;
//                        startActivity(intent);

                    }
                } else {

                    Utils.hideLoadingIndicator();
                    Toast.makeText(getApplicationContext(), "Xảy ra vấn đề khi gửi dữ liệu. \nVui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Recognitions> call, Throwable t) {
                Utils.hideLoadingIndicator();
                Toast.makeText(getApplicationContext(), "Gửi thất bại. \nVui lòng thử lại. " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
