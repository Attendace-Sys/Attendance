
package com.project.attendance.Networking;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Schedules {

    @SerializedName("schedule")
    private List<Schedule> mSchedule;

    public List<Schedule> getSchedule() {
        return mSchedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        mSchedule = schedule;
    }

}
