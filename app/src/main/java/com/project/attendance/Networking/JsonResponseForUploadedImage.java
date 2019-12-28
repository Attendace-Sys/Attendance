
package com.project.attendance.Networking;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JsonResponseForUploadedImage {

    @SerializedName("face_rect")
    private ArrayList<FaceRect> mFaceRect;
    @SerializedName("img_name")
    private String mImgName;

    public ArrayList<FaceRect> getFaceRect() {
        return mFaceRect;
    }

    public void setFaceRect(ArrayList<FaceRect> faceRect) {
        mFaceRect = faceRect;
    }

    public String getImgName() {
        return mImgName;
    }

    public void setImgName(String imgName) {
        mImgName = imgName;
    }

}
