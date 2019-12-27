
package com.project.attendance.Networking;

import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("name")
    private String mName;
    @SerializedName("recognized")
    private Long mRecognized;
    @SerializedName("score")
    private Double mScore;
    @SerializedName("student_code")
    private String mStudentCode;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getRecognized() {
        return mRecognized;
    }

    public void setRecognized(Long recognized) {
        mRecognized = recognized;
    }

    public Double getScore() {
        return mScore;
    }

    public void setScore(Double score) {
        mScore = score;
    }

    public String getStudentCode() {
        return mStudentCode;
    }

    public void setStudentCode(String studentCode) {
        mStudentCode = studentCode;
    }

}
