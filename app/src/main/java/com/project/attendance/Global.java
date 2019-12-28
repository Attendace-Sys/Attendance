package com.project.attendance;

import android.graphics.Bitmap;

import com.project.attendance.Networking.JsonResponseForUploadedImage;
import com.project.attendance.Networking.ResultRegconition;

import java.util.ArrayList;

public class Global {
    public static String token;
    public static String teacherCode;
    public static String teacherName;
    public static String email;

    public static ArrayList<ResultRegconition> listResult;
    public static String nowScheduleCode;
    public static ArrayList<Bitmap> listBitmap;
    public static ArrayList<String> imageNames;
    public static ArrayList<JsonResponseForUploadedImage> jsonResponseForUploadedImages;
}
