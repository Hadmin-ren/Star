package com.example.platform;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener{

    private List<Icon> IconList=new ArrayList<>();

    private LinearLayout first_layout;

    private LinearLayout second_layout;

    private LinearLayout third_layout;

    private LinearLayout forth_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(this.getActivity()).inflate(R.layout.fragment_main,null);
        first_layout = (LinearLayout)view.findViewById(R.id.first_layout);
        second_layout = (LinearLayout)view.findViewById(R.id.second_layout);
        third_layout = (LinearLayout)view.findViewById(R.id.third_layout);
        forth_layout = (LinearLayout)view.findViewById(R.id.forth_layout);
        first_layout.setOnClickListener(this);
        second_layout.setOnClickListener(this);
        third_layout.setOnClickListener(this);
        forth_layout.setOnClickListener(this);
        initIcons();
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        iconAdapter adapter=new iconAdapter(IconList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.first_layout:
            case R.id.second_layout:
            case R.id.third_layout:
            case R.id.forth_layout:
                Intent intent = new Intent(getContext(), VideoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initIcons(){
        Icon zero=new Icon("我的微专业",R.drawable.ic_category_0);
        IconList.add(zero);
        Icon one=new Icon("滴，打卡",R.drawable.ic_category_1);
        IconList.add(one);
        Icon two=new Icon("期末结业证书",R.drawable.ic_category_2);
        IconList.add(two);
        Icon three=new Icon("免费好课",R.drawable.ic_category_3);
        IconList.add(three);
    }

}
