package com.project.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.attendance.Adapter.CourseCardDataAdapter;
import com.project.attendance.Model.CourseCard;
import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;
import com.project.attendance.Networking.Courses;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.project.attendance.Networking.Course;

public class HomeFragment extends Fragment {

    ArrayList<CourseCard> listCourse;
    RecyclerView courseRecyclerView;

    String teacherId, password, token;

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(String username, String pass, String token)
    {
        this.teacherId = username;
        this.password = pass;
        this.token = token;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        courseRecyclerView = (RecyclerView) view.findViewById(R.id.courses_recyclerView);

        listCourse = new ArrayList<CourseCard>();

        callApi();

        return view;
    }

    private void callApi() {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<Courses> call = getResponse.getListCourse("Token "+ Global.token, Global.teacherCode);
        call.enqueue(new Callback<Courses>() {
            @Override
            public void onResponse(Call<Courses> call, Response<Courses> response) {
                if(response.isSuccessful()) {

                    Courses courses = (Courses) response.body();
                    listCourse = convertClassesFromCourses(courses);
                    addCourses();

                }
            }

            private ArrayList<CourseCard> convertClassesFromCourses(Courses courses) {

                ArrayList<CourseCard> list = new ArrayList<>();

                for ( Course item : courses.getClasses()) {

                    CourseCard course = convertClassToCourse(item);
                    list.add(course);
                }

                return list;
            }


    private CourseCard convertClassToCourse(Course item) {

                String id = item.getCourseCode();
                String name = item.getCourseName();
                String teacherID = item.getTeacher();
                String time = "Thứ "+ item.getDayOfWeek();
                String room = "";
                String status = "";
                CourseCard course = new CourseCard(id, name, teacherID, time, room, status);
                return course;
            }

            @Override
            public void onFailure(Call<Courses> call, Throwable t) {

            }
        });
    }

    private void addCourses() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        courseRecyclerView.setLayoutManager(linearLayoutManager);
        courseRecyclerView.setItemAnimator(new DefaultItemAnimator());


        CourseCardDataAdapter adapter = new CourseCardDataAdapter(getActivity(), listCourse);
        courseRecyclerView.setAdapter(adapter);
    }
}
