package com.project.attendance.Model;

public class Checking {
    String classId;
    String className;
    String room;
    int numberOfWeek;
    String timeOfWeek;
    String scheduleCode;
    String date;
    int number_present;
    int number_absent;

    public Checking(String classId, String className, String room, int numberOfWeek, String timeOfWeek, String scheduleCode, String date, int number_present, int number_absent) {
        this.classId = classId;
        this.className = className;
        this.room = room;
        this.numberOfWeek = numberOfWeek;
        this.timeOfWeek = timeOfWeek;
        this.scheduleCode = scheduleCode;
        this.date = date;
        this.number_present = number_present;
        this.number_absent = number_absent;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getNumberOfWeek() {
        return numberOfWeek;
    }

    public void setNumberOfWeek(int numberOfWeek) {
        this.numberOfWeek = numberOfWeek;
    }

    public String getTimeOfWeek() {
        return timeOfWeek;
    }

    public void setTimeOfWeek(String timeOfWeek) {
        this.timeOfWeek = timeOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getScheduleCode() {
        return scheduleCode;
    }

    public void setScheduleCode(String cheduleCode) {
        this.scheduleCode = cheduleCode;
    }
}
