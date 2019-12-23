package com.project.attendance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.attendance.Networking.ApiConfig;
import com.project.attendance.Networking.AppConfig;
import com.project.attendance.Networking.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final int CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7777;

    TextView teacherId, studentName, studentEmail;
    Button editProfile;

    String id, name, mail, token, password;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(String username, String password, String token, String email, String name)
    {
        this.id = username;
        this.password = password;
        this.token = token;
        this.mail = email;
        this.name = name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        teacherId = (TextView) view.findViewById(R.id.teacherIdTxt2);
        studentName = (TextView) view.findViewById(R.id.fullNameTxt2);
        studentEmail = (TextView) view.findViewById(R.id.emailTxt2);
        editProfile = (Button) view.findViewById(R.id.edit_profile_btn);

        teacherId.setText(id);
        studentName.setText(name);
        studentEmail.setText(mail);



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void logout() {
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<ResponseBody> call = getResponse.logout();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                Toast.makeText(getActivity(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
            }

        }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
