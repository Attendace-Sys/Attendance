package com.project.attendance.Networking;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiConfig {

    @Multipart
    @POST("api/v1/auth/login/")
    Call<User> login(@Part("username") RequestBody username,
                     @Part("password") RequestBody password);

    @POST("api/v1/auth/logout/")
    Call<ResponseBody> logout();


    @GET("api/v1/courses/teacher/{teacherid}")
    Call<Courses> getListCourse(@Header("Authorization") String authorization,
            @Path("teacherid") String id);


    @GET("api/v1/schedules/course/{courseid}")
    Call<Schedules> getListSchedule(@Header("Authorization") String authorization,
                                @Path("courseid") String courseid);


    @GET("api/v1/attendances/schedule/{scheduleid}")
    Call<Attendances> getListAttendanceOfOneSchedule(@Header("Authorization") String authorization,
                                    @Path("scheduleid") String scheduleid);

    @POST("api/v1/updatelist/")
    Call<ResponseBody> sendUpdateAttendanceList(@Header("Authorization") String authorization,
                                                @Body JsonObject data);
}
