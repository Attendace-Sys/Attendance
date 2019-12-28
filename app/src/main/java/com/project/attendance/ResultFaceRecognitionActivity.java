package com.project.attendance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.attendance.Adapter.ResultAttendanceDataAdapter;
import com.project.attendance.Model.AttendanceCard;
import com.project.attendance.Model.ResultAttendanceCard;
import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;
import com.project.attendance.Networking.Attendance;
import com.project.attendance.Networking.Attendances;
import com.project.attendance.Networking.DataAttendSend;
import com.project.attendance.Networking.UpdateAttendance;
import com.project.attendance.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultFaceRecognitionActivity extends AppCompatActivity {

    TextView m_name_class, m_id_class, m_number_week, m_time_week, m_date;
    TextView m_num_present, m_num_absent;
    RecyclerView list_attend_recyclerView;
    ImageView back_btn;
    Button reviewImage_btn;
    Button confirm_btn;

    String className, classId, room, timeOfWeek, dateAttend, scheduleCode;
    int numberOfWeek, numberPresent, numberAbsent;

    ArrayList<AttendanceCard> attendanceList;
    ArrayList<ResultAttendanceCard> resultAttendancesList;
//    ArrayList<ResultRegconition> listResult;

    ResultAttendanceDataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_face_recognition);

        m_name_class = (TextView) findViewById(R.id.courseNameTxt);
        m_id_class = (TextView) findViewById(R.id.class_id_txt);
        m_number_week = (TextView) findViewById(R.id.number_week_txt);
        m_time_week = (TextView) findViewById(R.id.time_week_txt);
        m_date = (TextView) findViewById(R.id.date_txt);
        m_num_present = (TextView) findViewById(R.id.num_presnt_txt);
        m_num_absent = (TextView) findViewById(R.id.num_absent_txt);
        confirm_btn = (Button) findViewById(R.id.senddata_btn);
        reviewImage_btn = (Button) findViewById(R.id.btn_reviewImage);

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
        m_number_week.setText("Tuần " + numberOfWeek);
        m_time_week.setText(timeOfWeek);
        m_date.setText(dateAttend);
//        m_num_present.setText(String.valueOf(numberPresent));
//        m_num_absent.setText(String.valueOf(numberAbsent));

        attendanceList = new ArrayList<AttendanceCard>();
        callApi();

//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Không có thông tin nào thay đổi.");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentBack = new Intent(ResultFaceRecognitionActivity.this, DetailCourseActivity.class);
//
//                intentBack.putExtra("iId", classId);
//                intentBack.putExtra("iName", className);
//                intentBack.putExtra("iTime", timeOfWeek);
//                intentBack.putExtra("iRoom", room);
//
//                startActivity(intentBack);
            }
        });

        reviewImage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ImagePopActivity.class);
//                intent.putExtra("scheduleCode", scheduleCode);
                startActivity(intent);

            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AttendanceCard> updateListItem = adapter.getUpdateList();

                if (updateListItem.size() != 0)
                {
                    String message = "  Đang gửi dữ liệu.\n  Vui lòng chờ...";
                    Utils.showLoadingIndicator(ResultFaceRecognitionActivity.this, message);

                    sendUpdateData(updateListItem);
                }
            }
        });
    }



    private void sendUpdateData(ArrayList<AttendanceCard> updateListItem) {

        List<UpdateAttendance> listUpdateAttend = new ArrayList<UpdateAttendance>();


        for (AttendanceCard item: updateListItem)
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

        callUploadApi(convertedObject);

    }

    private void callUploadApi(JsonObject json) {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<ResponseBody> call = getResponse.sendUpdateAttendanceList("Token "+ Global.token, json);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {

                    Utils.hideLoadingIndicator();
//                    Toast.makeText(ResultFaceRecognitionActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResultFaceRecognitionActivity.this, DetailAttendanceActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("className",className);
                    intent.putExtra("room", room);
                    intent.putExtra("numberOfWeek", numberOfWeek);
                    intent.putExtra("timeOfWeek", timeOfWeek);
                    intent.putExtra("scheduleCode", scheduleCode);
                    intent.putExtra("date", dateAttend);
                    intent.putExtra("numberPresent", numberPresent);
                    intent.putExtra("numberAbsent", numberAbsent);
                    startActivity(intent);


                }else
                {
                    Utils.hideLoadingIndicator();
                    Toast.makeText(ResultFaceRecognitionActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.hideLoadingIndicator();
                Toast.makeText(ResultFaceRecognitionActivity.this, "Có vấn đề xảy ra.", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private UpdateAttendance convertUpdateAttendanceFromAttendance(AttendanceCard item) {
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

                    resultAttendancesList = new ArrayList<ResultAttendanceCard>();
                    if ((Global.listResult != null) && (Global.nowScheduleCode.equals(scheduleCode) == true) && (attendanceList.size() == Global.listResult.size()))
                    {

                        for (int i = 0; i< Global.listResult.size(); i++ )
                        {
                            for( AttendanceCard item: attendanceList)
                            {
                                if (item.getStudentId().equals(Global.listResult.get(i).getStudentCode()))
                                {
                                    Boolean isPresent = (Global.listResult.get(i).getRecognized() > 0) ? true : false;
                                    ResultAttendanceCard resultAttendance = new ResultAttendanceCard(item.getAttendanceCode(), item.getStudentId(), item.getStudentName(), isPresent, Global.listResult.get(i).getScore());
                                    resultAttendancesList.add(resultAttendance);
                                }
                            }
                        }
                        addAttendance();
                        setNumPresent();
                    }
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private ArrayList<AttendanceCard> convertClassesFromCourses(Attendances attendances) {

                ArrayList<AttendanceCard> list = new ArrayList<>();

                for ( Attendance item : attendances.getAttends()) {

                    AttendanceCard attendance = convertClassToCourse(item);
                    list.add(attendance);
                }

                return list;
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            private AttendanceCard convertClassToCourse(Attendance item) {

                String attendanceCode = item.getAttendanceCode();
                String studentId = item.getStudent().getStudentCode();
                String studentName = item.getStudent().getFirstName();
                Boolean isPresent = item.getAbsentStatus();

                AttendanceCard attendance = new AttendanceCard(attendanceCode, studentId, studentName, isPresent);
                return attendance;
            }

            @Override
            public void onFailure(Call<Attendances> call, Throwable t) {

            }
        });

    }

    private void setNumPresent() {
//        attendanceList = adapter.getUpdateList();
        int sumStudent = resultAttendancesList.size();
        int count = 0;
        for ( ResultAttendanceCard item : resultAttendancesList) {
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

        adapter = new ResultAttendanceDataAdapter(this, resultAttendancesList);
        list_attend_recyclerView.setAdapter(adapter);
    }

    private Bitmap drawBitmapWithRetangle(Bitmap b, Rect rect, String name) {
        Bitmap bmOverlay = Bitmap.createBitmap(b.getWidth(), b.getHeight(), b.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawBitmap(b, 0, 0, null);
        return bmOverlay;
    }

}
