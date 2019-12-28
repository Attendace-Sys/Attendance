
package com.project.attendance.Networking;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Courses {

    @SerializedName("classes")
    private List<Course> mClasses;

    public List<Course> getClasses() {
        return mClasses;
    }

    public void setClasses(List<Course> classes) {
        mClasses = classes;
    }

}
