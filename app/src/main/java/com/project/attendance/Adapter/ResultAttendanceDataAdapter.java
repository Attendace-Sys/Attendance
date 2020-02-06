package com.project.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.attendance.Interface.CheckboxClickListener;
import com.project.attendance.Model.AttendanceCard;
import com.project.attendance.Model.ResultAttendanceCard;
import com.project.attendance.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultAttendanceDataAdapter extends RecyclerView.Adapter<ResultAttendanceDataAdapter.ViewHolder> {
    private ArrayList<ResultAttendanceCard> attendanceList;
    private ArrayList<AttendanceCard> updateList;
    private Context context;

    public ResultAttendanceDataAdapter(Context context, ArrayList<ResultAttendanceCard> list) {
        this.context = context;
        this.attendanceList = list;
        updateList = new ArrayList<AttendanceCard>();
        for (ResultAttendanceCard item : list) {
            AttendanceCard attendance = new AttendanceCard();
            attendance.setAttendanceCode(item.getAttendanceCode());
            attendance.setStudentId(item.getStudentId());
            attendance.setStudentName(item.getStudentName());
            attendance.setPresent(item.getPresent());
            updateList.add(attendance);
        }
    }

    @Override
    public ResultAttendanceDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_attendance, viewGroup, false);
        return new ResultAttendanceDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultAttendanceDataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.id.setText(attendanceList.get(i).getStudentId());
        viewHolder.name.setText(attendanceList.get(i).getStudentName());
        if (attendanceList.get(i).getPresent() == true) {
            viewHolder.isPresent.setChecked(true);
        } else {
            viewHolder.isPresent.setChecked(false);
        }
        Double score = attendanceList.get(i).getScore();
        if (score <= 0.04) {
            viewHolder.layout_color.setBackgroundColor(0xFF84F597);
        } else if (score > 0.04 && score < 0.06) {
            viewHolder.layout_color.setBackgroundColor(0xFFE6E906);
        } else if (score >= 0.06) {
            viewHolder.layout_color.setBackgroundColor(0xFFF8877F);
        }

        final int pos = i;
        viewHolder.isPresent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Boolean isch = viewHolder.isPresent.isChecked();

                attendanceList.get(pos).setPresent(isch);
                updateList.get(pos).setPresent(isch);

            }
        });
    }

    public ArrayList<ResultAttendanceCard> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(ArrayList<ResultAttendanceCard> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public ArrayList<AttendanceCard> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(ArrayList<AttendanceCard> updateList) {
        this.updateList = updateList;
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name;
        CheckBox isPresent;
        CheckboxClickListener checkboxClickListener;
        LinearLayout layout_color;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.student_id_txt);
            name = (TextView) view.findViewById(R.id.student_name_txt);
            isPresent = (CheckBox) view.findViewById(R.id.present_chbox);
            layout_color = (LinearLayout) view.findViewById(R.id.layout_color);

        }

    }

}