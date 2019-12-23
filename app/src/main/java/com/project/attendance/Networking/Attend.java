
package com.project.attendance.Networking;

import com.google.gson.annotations.SerializedName;

public class Attend {

    @SerializedName("absent_status")
    private Boolean mAbsentStatus;
    @SerializedName("attendance_code")
    private String mAttendanceCode;
    @SerializedName("image_data")
    private Object mImageData;
    @SerializedName("schedule_code")
    private String mScheduleCode;
    @SerializedName("student")
    private Student mStudent;

    public Boolean getAbsentStatus() {
        return mAbsentStatus;
    }

    public void setAbsentStatus(Boolean absentStatus) {
        mAbsentStatus = absentStatus;
    }

    public String getAttendanceCode() {
        return mAttendanceCode;
    }

    public void setAttendanceCode(String attendanceCode) {
        mAttendanceCode = attendanceCode;
    }

    public Object getImageData() {
        return mImageData;
    }

    public void setImageData(Object imageData) {
        mImageData = imageData;
    }

    public String getScheduleCode() {
        return mScheduleCode;
    }

    public void setScheduleCode(String scheduleCode) {
        mScheduleCode = scheduleCode;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

}
