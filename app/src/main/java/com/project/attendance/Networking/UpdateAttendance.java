
package com.project.attendance.Networking;


import com.google.gson.annotations.SerializedName;


public class UpdateAttendance {
    @SerializedName("attendance_code")
    private String mAttendanceCode;
    @SerializedName("student_code")
    private String mStudentCode;
    @SerializedName("absent_status")
    private Boolean mAbsentStatus;

    public UpdateAttendance(String mAttendanceCode, String mStudentCode, Boolean mAbsentStatus) {
        this.mAttendanceCode = mAttendanceCode;
        this.mStudentCode = mStudentCode;
        this.mAbsentStatus = mAbsentStatus;
    }

    public String getmAttendanceCode() {
        return mAttendanceCode;
    }

    public void setmAttendanceCode(String mAttendanceCode) {
        this.mAttendanceCode = mAttendanceCode;
    }

    public String getmStudentCode() {
        return mStudentCode;
    }

    public void setmStudentCode(String mStudentCode) {
        this.mStudentCode = mStudentCode;
    }

    public Boolean getmAbsentStatus() {
        return mAbsentStatus;
    }

    public void setmAbsentStatus(Boolean mAbsentStatus) {
        this.mAbsentStatus = mAbsentStatus;
    }
}
