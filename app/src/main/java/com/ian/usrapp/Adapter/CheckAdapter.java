package com.ian.usrapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ian.usrapp.CheckActivity;
import com.ian.usrapp.Obj.Check;
import com.ian.usrapp.R;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
    List<Check> datas;
    Context c ;
    public CheckAdapter(List<Check> datas)
    {this.datas = datas;}

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_activity_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_activity_title= itemView.findViewById(R.id.tv_activity_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Jacky",""+datas.get(getAdapterPosition()).toString());
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context c = parent.getContext();
        View v = LayoutInflater.from(c).inflate(R.layout.checkitem, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_activity_title.setText(datas.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }




}
