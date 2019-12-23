
package com.project.attendance.Networking;


import com.google.gson.annotations.SerializedName;


public class Schedule {

    @SerializedName("course")
    private String mCourse;
    @SerializedName("schedule_code")
    private String mScheduleCode;
    @SerializedName("schedule_date")
    private String mScheduleDate;
    @SerializedName("schedule_number_of_day")
    private Long mScheduleNumberOfDay;

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public String getScheduleCode() {
        return mScheduleCode;
    }

    public void setScheduleCode(String scheduleCode) {
        mScheduleCode = scheduleCode;
    }

    public String getScheduleDate() {
        return mScheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        mScheduleDate = scheduleDate;
    }

    public Long getScheduleNumberOfDay() {
        return mScheduleNumberOfDay;
    }

    public void setScheduleNumberOfDay(Long scheduleNumberOfDay) {
        mScheduleNumberOfDay = scheduleNumberOfDay;
    }

}
