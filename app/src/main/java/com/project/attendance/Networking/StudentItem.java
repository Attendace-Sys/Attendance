
package com.project.attendance.Networking;

import com.google.gson.annotations.SerializedName;

public class StudentItem {

    @SerializedName("num_absent")
    private Long mNumAbsent;
    @SerializedName("num_present")
    private Long mNumPresent;
    @SerializedName("students__first_name")
    private String mStudentsFirstName;
    @SerializedName("students__student_code")
    private String mStudentsStudentCode;

    public Long getNumAbsent() {
        return mNumAbsent;
    }

    public void setNumAbsent(Long numAbsent) {
        mNumAbsent = numAbsent;
    }

    public Long getNumPresent() {
        return mNumPresent;
    }

    public void setNumPresent(Long numPresent) {
        mNumPresent = numPresent;
    }

    public String getStudentsFirstName() {
        return mStudentsFirstName;
    }

    public void setStudentsFirstName(String studentsFirstName) {
        mStudentsFirstName = studentsFirstName;
    }

    public String getStudentsStudentCode() {
        return mStudentsStudentCode;
    }

    public void setStudentsStudentCode(String studentsStudentCode) {
        mStudentsStudentCode = studentsStudentCode;
    }

}
