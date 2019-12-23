
package com.project.attendance.Networking;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Courses {

    @SerializedName("classes")
    private List<Class> mClasses;

    public List<Class> getClasses() {
        return mClasses;
    }

    public void setClasses(List<Class> classes) {
        mClasses = classes;
    }

}
