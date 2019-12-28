
package com.project.attendance.Networking;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Attendances {

    @SerializedName("attends")
    private List<Attendance> mAttends;

    public List<Attendance> getAttends() {
        return mAttends;
    }

    public void setAttends(List<Attendance> attends) {
        mAttends = attends;
    }

}
