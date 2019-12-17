package com.project.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.attendance.Adapter.AttendanceDataAdapter;
import com.project.attendance.Adapter.StudentDataAdapter;
import com.project.attendance.Model.Attendance;
import com.project.attendance.Model.Student;

import java.util.ArrayList;

public class ViewListStudentActivity extends AppCompatActivity {
    TextView m_name_class, m_id_class;
    RecyclerView list_student_recyclerView;
    ImageView back_btn;

    String className, classId, room, timeOfWeek;

    ArrayList<Student> studentList;

    //Example data
    String studentIdList[] = {"15520001", "15520002", "15520003", "15520004", "15520005", "15520006"};
    String studentNameList[] = {"Nguyễn Văn A", "Nguyễn Thị B", "Lê Đình C", "Trần Tuyết E", "Cao Khánh F", "Vũ Văn H"};
    int prsents[] = {10, 9, 10, 8, 10, 10};
    int absent[] = {0, 1, 0, 2, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_student);

        m_name_class = (TextView) findViewById(R.id.courseNameTxt);
        m_id_class = (TextView) findViewById(R.id.class_id_txt);

        list_student_recyclerView = (RecyclerView) findViewById(R.id.list_student_recyclerView);

        back_btn = (ImageView) findViewById(R.id.back);

        final Intent intent = getIntent();
        className = intent.getStringExtra("className");
        classId = intent.getStringExtra("classId");
        room = intent.getStringExtra("room");
        timeOfWeek = intent.getStringExtra("timeOfWeek");

        m_name_class.setText(className);
        m_id_class.setText(classId);

        studentList = new ArrayList<Student>();

        addStudent();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(ViewListStudentActivity.this, DetailCourseActivity.class);

                intentBack.putExtra("iId", classId);
                intentBack.putExtra("iName", className);
                intentBack.putExtra("iTime", timeOfWeek);
                intentBack.putExtra("iRoom", room);

                startActivity(intentBack);
            }
        });

    }

    private void addStudent() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_student_recyclerView.setLayoutManager(linearLayoutManager);
        list_student_recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < studentIdList.length; i++)
        {
            Student student = new Student();
            student.setStudentId(studentIdList[i]);
            student.setStudentName(studentNameList[i]);
            student.setNumber_present(prsents[i]);
            student.setNumber_absent(absent[i]);

            studentList.add(student);
        }

        StudentDataAdapter adapter = new StudentDataAdapter(this, studentList);
        list_student_recyclerView.setAdapter(adapter);
    }
}
