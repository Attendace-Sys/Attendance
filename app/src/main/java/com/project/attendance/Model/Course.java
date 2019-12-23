package com.project.attendance.Model;

public class Course {
    String id;
    String name;
    String teacher;
    String time;
    String room;
    String status;

    public Course(String id, String name, String teacher, String time, String room, String status) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.time = time;
        this.room = room;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
