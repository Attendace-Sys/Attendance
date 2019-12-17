package com.project.attendance.Model;

public class Student {
    String studentId;
    String studentName;
    String email;
    int number_present;
    int number_absent;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber_present() {
        return number_present;
    }

    public void setNumber_present(int number_present) {
        this.number_present = number_present;
    }

    public int getNumber_absent() {
        return number_absent;
    }

    public void setNumber_absent(int number_absent) {
        this.number_absent = number_absent;
    }
}
