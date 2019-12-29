package com.project.attendance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.attendance.Interface.CheckboxClickListener;
import com.project.attendance.Model.AttendanceCard;
import com.project.attendance.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceDataAdapter extends RecyclerView.Adapter<AttendanceDataAdapter.ViewHolder>{
    private ArrayList<AttendanceCard> attendanceList;
    private ArrayList<AttendanceCard> tempAttendanceList;
    private ArrayList<AttendanceCard> updateList;
    private Context context;

    public AttendanceDataAdapter(Context context, ArrayList<AttendanceCard> list) {
        this.context = context;
        this.attendanceList = list;
        updateList = new ArrayList<AttendanceCard>();
        tempAttendanceList = attendanceList;
    }

    @Override
    public AttendanceDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_attendance, viewGroup, false);
        return new AttendanceDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceDataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.id.setText(attendanceList.get(i).getStudentId());
        viewHolder.name.setText(attendanceList.get(i).getStudentName());
        if (attendanceList.get(i).getPresent() == true)
        {
            viewHolder.isPresent.setChecked(true);
        }
        else
        {
            viewHolder.isPresent.setChecked(false);
        }

        final int pos = i;
        viewHolder.isPresent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Boolean isch = viewHolder.isPresent.isChecked();

                AttendanceCard attendance = attendanceList.get(pos);
                attendance.setPresent(isch);

                tempAttendanceList.get(pos).setPresent(isch);

                int index = updateList.indexOf(attendance);
                if (index == -1) {
                    updateList.add(attendance);
                } else {
                    updateList.get(index).setPresent(isch);
                }
            }
        });
    }

    public ArrayList<AttendanceCard> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(ArrayList<AttendanceCard> updateList) {
        this.updateList = updateList;
    }

    public ArrayList<AttendanceCard> getTempAttendanceList() {
        return tempAttendanceList;
    }

    public void setTempAttendanceList(ArrayList<AttendanceCard> tempAttendanceList) {
        this.tempAttendanceList = tempAttendanceList;
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, name;
        CheckBox isPresent;
        CheckboxClickListener checkboxClickListener;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.student_id_txt);
            name = (TextView) view.findViewById(R.id.student_name_txt);
            isPresent = (CheckBox) view.findViewById(R.id.present_chbox);
        }
    }
}
