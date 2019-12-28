package com.project.attendance.Model;

public class AttendanceCard {

    String attendanceCode;
    String studentId;
    String studentName;
    Boolean isPresent;

    public AttendanceCard(){

    }

    public AttendanceCard(String attendanceCode, String studentId, String studentName, Boolean isPresent) {
        this.attendanceCode = attendanceCode;
        this.studentId = studentId;
        this.studentName = studentName;
        this.isPresent = isPresent;
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
}
