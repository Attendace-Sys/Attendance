package com.project.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.attendance.Model.Attendance;
import com.project.attendance.Model.Student;
import com.project.attendance.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentDataAdapter extends RecyclerView.Adapter<StudentDataAdapter.ViewHolder> {
    private ArrayList<Student> studentList;
    private Context context;

    public StudentDataAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.studentList = list;
    }

    @Override
    public StudentDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_student, viewGroup, false);
        return new StudentDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentDataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.id.setText(studentList.get(i).getStudentId());
        viewHolder.name.setText(studentList.get(i).getStudentName());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, name;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.student_id_txt);
            name = (TextView) view.findViewById(R.id.student_name_txt);
        }

    }

}
