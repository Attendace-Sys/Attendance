
package com.project.attendance.Networking;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class DataAttendSend {
    @SerializedName("schedule_code")
    private String mScheduleCode;
    @SerializedName("data")
    private List<UpdateAttendance> mData;

    public DataAttendSend(String mScheduleCode, List<UpdateAttendance> mData) {
        this.mScheduleCode = mScheduleCode;
        this.mData = mData;
    }

    public String getmScheduleCode() {
        return mScheduleCode;
    }

    public void setmScheduleCode(String mScheduleCode) {
        this.mScheduleCode = mScheduleCode;
    }

    public List<UpdateAttendance> getmData() {
        return mData;
    }

    public void setmData(List<UpdateAttendance> mData) {
        this.mData = mData;
    }

}
