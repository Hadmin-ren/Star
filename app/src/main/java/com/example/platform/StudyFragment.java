package com.example.platform;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudyFragment extends androidx.fragment.app.Fragment {

    private List<Course> courseList = new ArrayList<>();

    public ImageView pinglun;

    private CourseAdapter courseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(this.getActivity()).inflate(R.layout.fragment_study,null);
        pinglun = view.findViewById(R.id.pinglun);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.course_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initCourses();
        pinglun.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PinglunActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initCourses(){
        Course c1 = new Course("项目开发实训", "张汇泉",
                "共计13课时|已学习3课时", R.drawable.c1);
        Course c2 = new Course("编译原理","刘汉强",
                "共计55课时|已学习15课时", R.drawable.c2);
        Course c3 = new Course("计算机网络","王涛",
                "共计45课时|已学习20课时", R.drawable.c3);
        Course c4 = new Course("数据库原理", "曹菡",
                "共计48课时|已学习39课时", R.drawable.c4);
        Course c5 = new Course("软件工程", "张莉",
                "共计45课时|已学习28课时", R.drawable.c5);
        Course c6 = new Course("概率论与数理统计", "王艳娥",
                "共计6课时|已学习6课时", R.drawable.c6);
        Course c7 = new Course("Java程序设计", "祁超",
                "共计100课时|已学习28课时", R.drawable.c7);
        Course c8 = new Course("网络安全", "马君亮",
                "共计42课时|已学习1课时", R.drawable.c8);
        Course c9 = new Course("Android开发", "田丰",
                "共计36课时|已学习35课时", R.drawable.c9);
        Course c10 = new Course("算法程序设计", "张立臣",
                "共计91课时|已学习45课时", R.drawable.c10);
        Course c11 = new Course("创新与发明", "江帆",
                "共计70课时|已学习11课时", R.drawable.c11);
        courseList.add(c1);
        courseList.add(c2);
        courseList.add(c3);
        courseList.add(c4);
        courseList.add(c5);
        courseList.add(c6);
        courseList.add(c7);
        courseList.add(c8);
        courseList.add(c9);
        courseList.add(c10);
        courseList.add(c11);
    }
}
