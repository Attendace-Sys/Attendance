package com.project.attendance.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.attendance.DetailCourseActivity;
import com.project.attendance.Interface.CardClickListener;
import com.project.attendance.Model.CourseCard;
import com.project.attendance.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseCardDataAdapter extends RecyclerView.Adapter<CourseCardDataAdapter.ViewHolder>
{

    private ArrayList<CourseCard> courses;
    private Context context;

    public CourseCardDataAdapter(Context context, ArrayList<CourseCard> listCourse) {
        this.context = context;
        this.courses = listCourse;
    }

    @Override
    public CourseCardDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card, viewGroup, false);
        return new CourseCardDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(courses.get(i).getName() + " - " + courses.get(i).getId());
        viewHolder.time.setText(courses.get(i).getTime());
        viewHolder.room.setText(courses.get(i).getRoom());

        if ((i % 5) == 1)
        {
            viewHolder.imageCourse.setImageResource(R.drawable.course2);
        } else if ((i % 5) == 2)
        {
            viewHolder.imageCourse.setImageResource(R.drawable.course3);
        } else if ((i % 5) == 3)
        {
            viewHolder.imageCourse.setImageResource(R.drawable.course3);
        }

        /*Use when you want to view detail on each card click*/
        viewHolder.setCardClickListener(new CardClickListener() {
            @Override
            public void onCardClick(View v, int pos) {
                String id = courses.get(pos).getId();
                String name = courses.get(pos).getName();
                String time = courses.get(pos).getTime();
                String room = courses.get(pos).getRoom();

                Intent intent = new Intent(context, DetailCourseActivity.class);
                intent.putExtra("iId", id);
                intent.putExtra("iName", name);
                intent.putExtra("iTime", time);
                intent.putExtra("iRoom", room);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, time, room, status;
        CardClickListener cardClickListener;
        ImageView imageCourse;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_course_txt);
            time = (TextView) view.findViewById(R.id.class_time_txt);
            room = (TextView) view.findViewById(R.id.room_txt);
            imageCourse = (ImageView) view.findViewById(R.id.imageCourse);
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
