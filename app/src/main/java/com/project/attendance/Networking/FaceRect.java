
package com.project.attendance.Networking;

import com.google.gson.annotations.SerializedName;

public class FaceRect {

    @SerializedName("bottom")
    private Long mBottom;
    @SerializedName("left")
    private Long mLeft;
    @SerializedName("name")
    private String mName;
    @SerializedName("right")
    private Long mRight;
    @SerializedName("score")
    private Double mScore;
    @SerializedName("student_id")
    private String mStudentId;
    @SerializedName("top")
    private Long mTop;

    public Long getBottom() {
        return mBottom;
    }

    public void setBottom(Long bottom) {
        mBottom = bottom;
    }

    public Long getLeft() {
        return mLeft;
    }

    public void setLeft(Long left) {
        mLeft = left;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getRight() {
        return mRight;
    }

    public void setRight(Long right) {
        mRight = right;
    }

    public Double getScore() {
        return mScore;
    }

    public void setScore(Double score) {
        mScore = score;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
    }

    public Long getTop() {
        return mTop;
    }

    public void setTop(Long top) {
        mTop = top;
    }

}
