
package com.project.attendance.Networking;


import com.google.gson.annotations.SerializedName;


public class Student {

    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("student_code")
    private String mStudentCode;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getStudentCode() {
        return mStudentCode;
    }

    public void setStudentCode(String studentCode) {
        mStudentCode = studentCode;
    }

}
