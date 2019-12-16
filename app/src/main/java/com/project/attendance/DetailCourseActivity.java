package com.project.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.attendance.Adapter.CheckingCardDataAdapter;
import com.project.attendance.Model.Checking;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailCourseActivity extends AppCompatActivity {

    ArrayList<Checking> checkingList;
    RecyclerView checkingRecyclerView;
    Button btn_checking, btn_viewlist;

    TextView mNameCourseTv, mTimeTv, mRoomTv;
    ImageView back;

    String mId, mName, mTime, mRoom;

    String dates[] = {"Ngày 01/20/2109", "Ngày 22/11/2019", "Ngày 12/12/2019"};
    int number_prent[] = {30, 44, 48 };
    int number_absnet[] = {0, 1, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        //Actionbar
        //ActionBar actionBar = getSupportActionBar();

        mNameCourseTv = (TextView) findViewById(R.id.d_name_course_txt);
        mTimeTv = (TextView) findViewById(R.id.d_class_time_txt);
        mRoomTv = (TextView) findViewById(R.id.d_room_txt);
        back = (ImageView) findViewById(R.id.back);
        checkingRecyclerView = (RecyclerView) findViewById(R.id.list_checking_recyclerView);
        btn_checking = (Button) findViewById((R.id.btn_Cheking));
        btn_viewlist = (Button) findViewById(R.id.btn_viewlist);

        final Intent intent = getIntent();
        mId = intent.getStringExtra("iId");
        mName = intent.getStringExtra("iName");
        mTime = intent.getStringExtra("iTime");
        mRoom = intent.getStringExtra("iRoom");


        //actionBar.setTitle("Detail");
        mNameCourseTv.setText(mName);
        mTimeTv.setText(mTime);
        mRoomTv.setText(mRoom);

        checkingList = new ArrayList<Checking>();

        addChecking();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(DetailCourseActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        btn_checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(DetailCourseActivity.this, DetectActivity.class);
                startActivity(intentBack);
            }
        });

        btn_viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailCourseActivity.this, ViewListStudentActivity.class);
                intent.putExtra("classId", mId);
                intent.putExtra("className", mName);
                intent.putExtra("timeOfWeek", mTime);
                intent.putExtra("room", mRoom);

                startActivity(intent);
            }
        });
    }

    private void addChecking() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        checkingRecyclerView.setLayoutManager(linearLayoutManager);
        checkingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0; i < 3; i++)
        {
            Checking checking = new Checking();
            checking.setClassId(mId);
            checking.setClassName(mName);
            checking.setRoom(mRoom);
            checking.setNumberOfWeek(i + 1);
            checking.setTimeOfWeek(mTime);
            checking.setDate(dates[i]);
            checking.setNumber_present(number_prent[i]);
            checking.setNumber_absent(number_absnet[i]);

            checkingList.add(checking);
        }

        CheckingCardDataAdapter adapter = new CheckingCardDataAdapter(this, checkingList);
        checkingRecyclerView.setAdapter(adapter);
    }
}
