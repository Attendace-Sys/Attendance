package com.project.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.attendance.Adapter.CourseCardDataAdapter;
import com.project.attendance.Model.Course;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    ArrayList<Course> listCourse;
    RecyclerView courseRecyclerView;

    String idCouorse[] = {"SE324.J23", "SE102.K12", "MA001.J21"};
    String nameCourse[] = {"Chuyên đề J2EE","Ứng dụng di động","Toán cao cấp 1"};
    String teachers[] = {"ThS. Trương Hùng", "TS. Cao Thị Vân", "ThS. Vũ Hồng Thúy"};
    String times[] = {"Thứ 2: Tiết 123", "Thứ 3: Tiết 678", "Thứ 7: Tiết 1234"};
    String rooms[] = {"Phòng A11.09", "Phòng B05.12", "Phòng C102"};
    String statuses[] = {"Đang học", "Đang học", "Đang học"};

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        courseRecyclerView = (RecyclerView) view.findViewById(R.id.courses_recyclerView);

        listCourse = new ArrayList<Course>();

        addCourses();

        return view;
    }

    private void addCourses() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        courseRecyclerView.setLayoutManager(linearLayoutManager);
        courseRecyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < nameCourse.length; i++)
        {
            Course course = new Course();
            course.setId(idCouorse[i]);
            course.setName(nameCourse[i]);
            course.setTeacher(teachers[i]);
            course.setTime(times[i]);
            course.setRoom(rooms[i]);
            course.setStatus(statuses[i]);
            listCourse.add(course);
        }

        CourseCardDataAdapter adapter = new CourseCardDataAdapter(getActivity(), listCourse);
        courseRecyclerView.setAdapter(adapter);
    }
}
