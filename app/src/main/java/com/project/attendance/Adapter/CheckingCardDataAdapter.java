package com.project.attendance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.attendance.DetailAttendanceActivity;
import com.project.attendance.Interface.CardClickListener;
import com.project.attendance.Model.Checking;
import com.project.attendance.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CheckingCardDataAdapter extends RecyclerView.Adapter<CheckingCardDataAdapter.ViewHolder>{
    private ArrayList<Checking> checkingList;
    private Context context;

    public CheckingCardDataAdapter(Context context, ArrayList<Checking> list) {
        this.context = context;
        this.checkingList = list;
    }

    @Override
    public CheckingCardDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checking_card, viewGroup, false);
        return new CheckingCardDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckingCardDataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.week.setText("#" + checkingList.get(i).getNumberOfWeek());
        viewHolder.date.setText(checkingList.get(i).getDate());
        viewHolder.number_present.setText("Có mặt: "+ checkingList.get(i).getNumber_present());
        viewHolder.number_absent.setText("Vắng: " + checkingList.get(i).getNumber_absent());

        viewHolder.setCardClickListener(new CardClickListener() {
            @Override
            public void onCardClick(View v, int pos) {
                Intent intent = new Intent(context, DetailAttendanceActivity.class);
                intent.putExtra("classId", checkingList.get(pos).getClassId());
                intent.putExtra("className", checkingList.get(pos).getClassName());
                intent.putExtra("room", checkingList.get(pos).getRoom());
                intent.putExtra("numberOfWeek", checkingList.get(pos).getNumberOfWeek());
                intent.putExtra("timeOfWeek", checkingList.get(pos).getTimeOfWeek());
                intent.putExtra("scheduleCode", checkingList.get(pos).getScheduleCode());
                intent.putExtra("date", checkingList.get(pos).getDate());
                intent.putExtra("numberPresent", checkingList.get(pos).getNumber_present());
                intent.putExtra("numberAbsent", checkingList.get(pos).getNumber_absent());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView week, date, number_present, number_absent;
        LinearLayout is_absent, is_not_absent;
        CardClickListener cardClickListener;

        public ViewHolder(View view) {
            super(view);
            week = (TextView) view.findViewById(R.id.number_week_txt);
            date = (TextView) view.findViewById(R.id.date_txt);
            number_present = (TextView) view.findViewById(R.id.number_present_txt);
            number_absent = (TextView) view.findViewById(R.id.number_absent_txt);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.cardClickListener.onCardClick(v, getAdapterPosition());
        }

        public void setCardClickListener(CardClickListener cardClick)
        {
            this.cardClickListener = cardClick;
        }
    }
}
