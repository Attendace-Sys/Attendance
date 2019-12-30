package com.project.attendance.Networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppConfig {

    //public static String BASE_URL = "https://deploy-attendance-release-ver-3.azurewebsites.net/";
    public static String BASE_URL = "http://192.168.1.8:8000/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        CookieHandler cookieHandler = new CookieManager();

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();
//                    .addNetworkInterceptor(interceptor)
//                    .cookieJar(new JavaNetCookieJar(cookieHandler))

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
