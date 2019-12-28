package com.project.attendance.Model;

public class ResultAttendanceCard {
    String attendanceCode;
    String studentId;
    String studentName;
    Boolean isPresent;
    Double score;

    public ResultAttendanceCard(String attendanceCode, String studentId, String studentName, Boolean isPresent, Double score) {
        this.attendanceCode = attendanceCode;
        this.studentId = studentId;
        this.studentName = studentName;
        this.isPresent = isPresent;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public String getAttendanceCode() {
        return attendanceCode;
    }

    public void setAttendanceCode(String attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
