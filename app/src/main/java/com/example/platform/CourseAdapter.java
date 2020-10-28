package com.example.platform;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.platform.activity.Main3Activity;

import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private Context mContext;

    private List<Course> mCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView courseImage;
        TextView nameText;
        TextView teacherText;
        TextView introductionText;
        LinearLayout courseLinear;
        LinearLayout examLinear;

        public ViewHolder(View view){
            super(view);
            courseImage = (ImageView)view.findViewById(R.id.course_image);
            nameText = (TextView)view.findViewById(R.id.course_name);
            teacherText = (TextView)view.findViewById(R.id.course_teacher);
            introductionText = (TextView)view.findViewById(R.id.course_introduction);
            courseLinear = (LinearLayout)view.findViewById(R.id.course_linear);
            examLinear = (LinearLayout)view.findViewById(R.id.exam_linear);
        }
    }

    public CourseAdapter(List<Course> courseList){
        mCourseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.courseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                Intent intent = new Intent(mContext, VideoActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.examLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                Intent intent = new Intent(mContext, Main3Activity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Course course = mCourseList.get(position);
        Glide.with(mContext).load(course.getImageId()).into(holder.courseImage);
        holder.nameText.setText(course.getName());
        holder.teacherText.setText(course.getTeacher());
        holder.introductionText.setText(course.getIntroduction());
    }

    @Override
    public int getItemCount(){
        return mCourseList.size();
    }

}
