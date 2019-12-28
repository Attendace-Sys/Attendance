package com.project.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.project.attendance.Adapter.StudentDataAdapter;
import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;
import com.project.attendance.Networking.ListStudentOfCourse;
import com.project.attendance.Networking.StudentItem;

import java.util.ArrayList;

public class ViewListStudentActivity extends AppCompatActivity {
    TextView m_name_class, m_id_class, m_num_learned;
    RecyclerView list_student_recyclerView;
    ImageView back_btn;

    String className, classId, room, timeOfWeek;

    ArrayList<StudentItem> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_student);

        m_name_class = (TextView) findViewById(R.id.courseNameTxt);
        m_id_class = (TextView) findViewById(R.id.class_id_txt);
        m_num_learned = (TextView) findViewById(R.id.num_learned_txt);

        list_student_recyclerView = (RecyclerView) findViewById(R.id.list_student_recyclerView);

        back_btn = (ImageView) findViewById(R.id.back);

        final Intent intent = getIntent();
        className = intent.getStringExtra("className");
        classId = intent.getStringExtra("classId");
        room = intent.getStringExtra("room");
        timeOfWeek = intent.getStringExtra("timeOfWeek");


        m_name_class.setText(className);
        m_id_class.setText(classId);

        studentList = new ArrayList<StudentItem>();

        callApi();

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

    void callApi()
    {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<ListStudentOfCourse> call = getResponse.getListStudentOfACourse("Token "+ Global.token, classId);
        call.enqueue(new Callback<ListStudentOfCourse>() {
            @Override
            public void onResponse(Call<ListStudentOfCourse> call, Response<ListStudentOfCourse> response) {
                if(response.isSuccessful()) {

                    ListStudentOfCourse listStudentOfCourse = (ListStudentOfCourse) response.body();

                    studentList = listStudentOfCourse.getAttends();

                    addStudent();

                    m_num_learned.setText( "" + (int)(studentList.get(0).getNumPresent() + studentList.get(0).getNumAbsent()));
                }
            }

            @Override
            public void onFailure(Call<ListStudentOfCourse> call, Throwable t) {

            }
        });
    }

    private void addStudent() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_student_recyclerView.setLayoutManager(linearLayoutManager);
        list_student_recyclerView.setItemAnimator(new DefaultItemAnimator());


        StudentDataAdapter adapter = new StudentDataAdapter(this, studentList);
        list_student_recyclerView.setAdapter(adapter);
    }
}
