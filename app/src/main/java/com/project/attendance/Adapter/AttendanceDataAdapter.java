package com.project.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.attendance.Model.Attendance;
import com.project.attendance.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceDataAdapter extends RecyclerView.Adapter<AttendanceDataAdapter.ViewHolder>{
    private ArrayList<Attendance> attendanceList;
    private Context context;

    public AttendanceDataAdapter(Context context, ArrayList<Attendance> list) {
        this.context = context;
        this.attendanceList = list;
    }

    @Override
    public AttendanceDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_attendance, viewGroup, false);
        return new AttendanceDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceDataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.id.setText(attendanceList.get(i).getStudentId());
        viewHolder.name.setText(attendanceList.get(i).getStudentName());
        if (attendanceList.get(i).getPresent() == false)
        {
            viewHolder.isPresent.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.isAbsent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, name, isPresent, isAbsent;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.student_id_txt);
            name = (TextView) view.findViewById(R.id.student_name_txt);
            isPresent = (TextView) view.findViewById(R.id.present_txt);
            isAbsent = (TextView) view.findViewById(R.id.abdent_txt);
        }

    }

}
