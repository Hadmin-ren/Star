package com.example.platform;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelfFragment extends Fragment {

    private TextView nameText;

    private TextView accountText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(this.getActivity()).inflate(R.layout.fragment_person,null);
        nameText=view.findViewById(R.id.t_name);
        nameText.setText(Help.name);
        accountText = view.findViewById(R.id.tv2);
        accountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
