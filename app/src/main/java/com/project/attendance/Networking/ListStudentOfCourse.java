
package com.project.attendance.Networking;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListStudentOfCourse {

    @SerializedName("attends")
    private ArrayList<StudentItem> mAttends;

    public ArrayList<StudentItem> getAttends() {
        return mAttends;
    }

    public void setAttends(ArrayList<StudentItem> attends) {
        mAttends = attends;
    }

}
