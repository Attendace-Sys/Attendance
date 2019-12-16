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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final int CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7777;

    TextView studentId, studentName, studentEmail;
    Button collectDatabtn;

    String id, name, mail;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        studentId = (TextView) view.findViewById(R.id.studentIdTxt2);
        studentName = (TextView) view.findViewById(R.id.fullNameTxt2);
        studentEmail = (TextView) view.findViewById(R.id.emailTxt2);

        id = (String) studentId.getText();
        name = (String) studentName.getText();
        mail = (String) studentEmail.getText();


        // Inflate the layout for this fragment
        return view;
    }

}
