
package com.project.attendance.Networking;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Attendances {

    @SerializedName("attends")
    private List<Attend> mAttends;

    public List<Attend> getAttends() {
        return mAttends;
    }

    public void setAttends(List<Attend> attends) {
        mAttends = attends;
    }

}
