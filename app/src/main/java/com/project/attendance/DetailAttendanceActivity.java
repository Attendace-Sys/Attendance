package com.project.attendance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.project.attendance.Adapter.AttendanceDataAdapter;
import com.project.attendance.Model.Attendance;
import com.project.attendance.Model.Checking;
import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;
import com.project.attendance.Networking.Attend;
import com.project.attendance.Networking.Attendances;
import com.project.attendance.Networking.DataAttendSend;
import com.project.attendance.Networking.Schedule;
import com.project.attendance.Networking.Schedules;
import com.project.attendance.Networking.UpdateAttendance;
import com.project.attendance.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailAttendanceActivity extends AppCompatActivity {

    TextView m_name_class, m_id_class, m_number_week, m_time_week, m_date;
    TextView m_num_present, m_num_absent;
    RecyclerView list_attend_recyclerView;
    ImageView back_btn;
    Button checking_btn;
    Button sendData_btn;

    String className, classId, room, timeOfWeek, dateAttend, scheduleCode;
    int numberOfWeek, numberPresent, numberAbsent;

    ArrayList<Attendance> attendanceList;

    AttendanceDataAdapter adapter;

//    //Example data
//    String studentIdList[] = {"15520001", "15520002", "15520003", "15520004", "15520005", "15520006"};
//    String studentNameList[] = {"Nguyá»…n VÄƒn A", "Nguyá»…n Thá»‹ B", "LÃª ÄÃ¬nh C", "Tráº§n Tuyáº¿t E", "Cao KhÃ¡nh F", "VÅ© VÄƒn H"};
//    Boolean isPresentList[] = {true, true, true, false, true, true};

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
        checking_btn = (Button) findViewById(R.id.checking_btn);
        sendData_btn = (Button) findViewById(R.id.senddata_btn);

        list_attend_recyclerView = (RecyclerView) findViewById(R.id.list_attend_recyclerView);

        back_btn = (ImageView) findViewById(R.id.back);

        final Intent intent = getIntent();
        className = intent.getStringExtra("className");
        classId = intent.getStringExtra("classId");
        room = intent.getStringExtra("room");
        numberOfWeek = intent.getIntExtra("numberOfWeek", 1);
        timeOfWeek = intent.getStringExtra("timeOfWeek");
        scheduleCode = intent.getStringExtra("scheduleCode");
        dateAttend = intent.getStringExtra("date");
        numberPresent = intent.getIntExtra("numberPresent", 0);
        numberAbsent = intent.getIntExtra("numberAbsent", 0);

        m_name_class.setText(className);
        m_id_class.setText(classId);
        m_number_week.setText("Tuáº§n " + numberOfWeek);
        m_time_week.setText(timeOfWeek);
        m_date.setText(dateAttend);
//        m_num_present.setText(String.valueOf(numberPresent));
//        m_num_absent.setText(String.valueOf(numberAbsent));


        attendanceList = new ArrayList<Attendance>();

        callApi();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("KhÃ´ng cÃ³ thÃ´ng tin nÃ o thay Ä‘á»•i.");

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

        checking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailAttendanceActivity.this, TakingPictureAttendanceActivity.class);
                intent.putExtra("scheduleCode", scheduleCode);
                startActivity(intent);

            }
        });

        sendData_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Attendance> updateListItem = adapter.getUpdateList();

                if (updateListItem.size() != 0)
                {
                    String message = "  Äang gá»­i dá»¯ liá»‡u.\n  Vui lÃ²ng chá»...";
                    Utils.showLoadingIndicator(DetailAttendanceActivity.this, message);

                    sendUpdateData(updateListItem);
                }
                else
                {
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }



    private void sendUpdateData(ArrayList<Attendance> updateListItem) {

        List<UpdateAttendance> listUpdateAttend = new ArrayList<UpdateAttendance>();


        for (Attendance item: updateListItem)
        {
            UpdateAttendance updateAttendance = convertUpdateAttendanceFromAttendance(item);
            listUpdateAttend.add(updateAttendance);
        }
        DataAttendSend dataAttendSend = new DataAttendSend(scheduleCode, listUpdateAttend);

        Gson gson = new Gson();

        String json = gson.toJson(dataAttendSend);

        JSONObject ob = new JSONObject();
        try {
            ob = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);

        callApi(convertedObject);

    }

    private void callApi(JsonObject json) {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<ResponseBody> call = getResponse.sendUpdateAttendanceList("Token "+ Global.token, json);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {

                    Utils.hideLoadingIndicator();
                    Toast.makeText(DetailAttendanceActivity.this, "ThÃ nh cÃ´ng", Toast.LENGTH_SHORT).show();

                }else
                {
                    Utils.hideLoadingIndicator();
                    Toast.makeText(DetailAttendanceActivity.this, "Tháº¥t báº¡i", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideLoadingIndicator();
                Toast.makeText(DetailAttendanceActivity.this, "Lá»—i xáº£y ra.", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private UpdateAttendance convertUpdateAttendanceFromAttendance(Attendance item) {
        String mAttendanceCode = item.getAttendanceCode();
        String mStudentCode = item.getStudentId();
        Boolean mAbsentStatus = item.getPresent();

        UpdateAttendance updateAttendance = new UpdateAttendance(mAttendanceCode, mStudentCode, mAbsentStatus);
        return updateAttendance;
    }

    private void callApi() {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<Attendances> call = getResponse.getListAttendanceOfOneSchedule("Token "+ Global.token, scheduleCode);
        call.enqueue(new Callback<Attendances>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Attendances> call, Response<Attendances> response) {
                if(response.isSuccessful()) {

                    Attendances attendances = (Attendances) response.body();
                    attendanceList = convertClassesFromCourses(attendances);
                    addAttendance();

                    setNumPresent();

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private ArrayList<Attendance> convertClassesFromCourses(Attendances attendances) {

                ArrayList<Attendance> list = new ArrayList<>();

                for ( Attend item : attendances.getAttends()) {

                    Attendance attendance = convertClassToCourse(item);
                    list.add(attendance);
                }

                return list;
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            private Attendance convertClassToCourse(Attend item) {

                String attendanceCode = item.getAttendanceCode();
                String studentId = item.getStudent().getStudentCode();
                String studentName = item.getStudent().getFirstName();
                Boolean isPresent = item.getAbsentStatus();

                Attendance attendance = new Attendance(attendanceCode, studentId, studentName, isPresent);
                return attendance;
            }

            @Override
            public void onFailure(Call<Attendances> call, Throwable t) {

            }
        });

    }

    private void setNumPresent() {
        int sumStudent = attendanceList.size();
        int count = 0;
        for ( Attendance item : attendanceList) {
            if (item.getPresent() == false)
                count ++;
        }

        int numPresent = sumStudent - count;

        m_num_present.setText(String.valueOf(numPresent));
        m_num_absent.setText(String.valueOf(count));

    }

    private void addAttendance() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list_attend_recyclerView.setLayoutManager(linearLayoutManager);
        list_attend_recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new AttendanceDataAdapter(this, attendanceList);
        list_attend_recyclerView.setAdapter(adapter);
    }
}
