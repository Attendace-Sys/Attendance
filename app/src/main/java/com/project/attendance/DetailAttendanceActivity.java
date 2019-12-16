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
import com.project.attendance.Model.Attendance;

import java.util.ArrayList;

public class DetailAttendanceActivity extends AppCompatActivity {

    TextView m_name_class, m_id_class, m_number_week, m_time_week, m_date;
    TextView m_num_present, m_num_absent;
    RecyclerView list_attend_recyclerView;
    ImageView back_btn;

    String className, classId, room, timeOfWeek, dateAttend;
    int numberOfWeek, numberPresent, numberAbsent;

    ArrayList<Attendance> attendanceList;

    //Example data
    String studentIdList[] = {"15520001", "15520002", "15520003", "15520004", "15520005", "15520006"};
    String studentNameList[] = {"Nguyễn Văn A", "Nguyễn Thị B", "Lê Đình C", "Trần Tuyết E", "Cao Khánh F", "Vũ Văn H"};
    Boolean isPresentList[] = {true, true, true, false, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_attendance);

        m_name_class = (TextView) findViewById(R.id.courseNameTxt);
        m_id_class = (TextView) findViewById(R.id.class_id_txt);
        m_number_week = (TextView) findViewById(R.id.number_week_txt);
        m_time_week = (TextView) findViewById(R.id.time_week_txt);
        m_date = (TextView) findViewById(R.id.date_txt);
        m_num_present = (TextView) findViewById(R.id.num_presnt_txt);
        m_num_absent = (TextView) findViewById(R.id.num_absent_txt);

        list_attend_recyclerView = (RecyclerView) findViewById(R.id.list_attend_recyclerView);

        back_btn = (ImageView) findViewById(R.id.back);

        final Intent intent = getIntent();
        className = intent.getStringExtra("className");
        classId = intent.getStringExtra("classId");
        room = intent.getStringExtra("room");
        numberOfWeek = intent.getIntExtra("numberOfWeek", 1);
        timeOfWeek = intent.getStringExtra("timeOfWeek");
        dateAttend = intent.getStringExtra("date");
        numberPresent = intent.getIntExtra("numberPresent", 0);
        numberAbsent = intent.getIntExtra("numberAbsent", 0);

        m_name_class.setText(className);
        m_id_class.setText(classId);
        m_number_week.setText("Tuần " + numberOfWeek);
        m_time_week.setText(timeOfWeek);
        m_date.setText(dateAttend);
//        m_num_present.setText(String.valueOf(numberPresent));
//        m_num_absent.setText(String.valueOf(numberAbsent));
        m_num_present.setText(String.valueOf(5));
        m_num_absent.setText(String.valueOf(1));

        attendanceList = new ArrayList<Attendance>();

        addAttendance();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(DetailAttendanceActivity.this, DetailCourseActivity.class);

                intentBack.putExtra("iId", classId);
                intentBack.putExtra("iName", className);
                intentBack.putExtra("iTime", timeOfWeek);
                intentBack.putExtra("iRoom", room);

                startActivity(intentBack);
            }
        });

    }

    private void addAttendance() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_attend_recyclerView.setLayoutManager(linearLayoutManager);
        list_attend_recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < studentIdList.length; i++)
        {
            Attendance attend = new Attendance();
            attend.setStudentId(studentIdList[i]);
            attend.setStudentName(studentNameList[i]);
            attend.setPresent(isPresentList[i]);

            attendanceList.add(attend);
        }

        AttendanceDataAdapter adapter = new AttendanceDataAdapter(this, attendanceList);
        list_attend_recyclerView.setAdapter(adapter);
    }
}
