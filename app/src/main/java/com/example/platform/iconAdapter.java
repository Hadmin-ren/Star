package com.example.platform;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class iconAdapter extends RecyclerView.Adapter<iconAdapter.ViewHolder> {
    private List<Icon> mIconList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView iconName;

        public ViewHolder(View view) {
            super(view);
            iconImage=(ImageView)view.findViewById(R.id.icon_image);
            iconName=(TextView)view.findViewById(R.id.icon_name);
        }
    }

    public iconAdapter(List<Icon> iconList){
        mIconList=iconList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Icon icon = mIconList.get(position);
        holder.iconImage.setImageResource(icon.getIconImageId());
        holder.iconName.setText(icon.getIconName());
    }

    @Override
    public int getItemCount(){
        return mIconList.size();
    }
}
