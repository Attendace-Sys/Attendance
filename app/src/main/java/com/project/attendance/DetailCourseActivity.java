package com.project.attendance;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.attendance.Adapter.CheckingCardDataAdapter;
import com.project.attendance.Model.CheckingCard;
import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;

import com.project.attendance.Networking.Schedule;
import com.project.attendance.Networking.Schedules;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCourseActivity extends AppCompatActivity {

    ArrayList<CheckingCard> checkingList;
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

        checkingList = new ArrayList<CheckingCard>();

        callApi();

//        addChecking();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(DetailCourseActivity.this, MainActivity.class);
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

    private void callApi() {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<Schedules> call = getResponse.getListSchedule("Token "+ Global.token, mId);
        call.enqueue(new Callback<Schedules>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Schedules> call, Response<Schedules> response) {
                if(response.isSuccessful()) {

                    Schedules schedules = (Schedules) response.body();
                    checkingList = convertClassesFromCourses(schedules);
                    addChecking();

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private ArrayList<CheckingCard> convertClassesFromCourses(Schedules schedules) {

                ArrayList<CheckingCard> list = new ArrayList<>();

                for ( Schedule item : schedules.getSchedule()) {

                    CheckingCard schedule = convertClassToCourse(item);
                    list.add(schedule);
                }

                return list;
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            private CheckingCard convertClassToCourse(Schedule item) {

                String classId = item.getCourse();
                String className = mName;
                String room = mRoom;
                int numberOfWeek = Math.toIntExact(item.getScheduleNumberOfDay());
                String timeOfWeek = mTime;
                String scheduleCode = item.getScheduleCode();
                String date = item.getScheduleDate();
                int number_present = 0;
                int number_absent = 0 ;
                CheckingCard checking = new CheckingCard(classId, className, room, numberOfWeek, timeOfWeek, scheduleCode, date, number_present, number_absent );
                return checking;
            }

            @Override
            public void onFailure(Call<Schedules> call, Throwable t) {

            }
        });

    }

    private void addChecking() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        checkingRecyclerView.setLayoutManager(linearLayoutManager);
        checkingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Collections.sort(checkingList);

        CheckingCardDataAdapter adapter = new CheckingCardDataAdapter(this, checkingList);
        checkingRecyclerView.setAdapter(adapter);
    }
}
