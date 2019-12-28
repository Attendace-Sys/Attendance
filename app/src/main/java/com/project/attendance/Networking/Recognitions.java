
package com.project.attendance.Networking;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Recognitions {

    @SerializedName("json_response_for_uploaded_images")
    private ArrayList<JsonResponseForUploadedImage> mJsonResponseForUploadedImages;
    @SerializedName("list_student")
    private ArrayList<ResultRegconition> mListStudent;

    public ArrayList<JsonResponseForUploadedImage> getJsonResponseForUploadedImages() {
        return mJsonResponseForUploadedImages;
    }

    public void setJsonResponseForUploadedImages(ArrayList<JsonResponseForUploadedImage> jsonResponseForUploadedImages) {
        mJsonResponseForUploadedImages = jsonResponseForUploadedImages;
    }

    public ArrayList<ResultRegconition> getListStudent() {
        return mListStudent;
    }

    public void setListStudent(ArrayList<ResultRegconition> listStudent) {
        mListStudent = listStudent;
    }

}
