package com.ian.usrapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ian.usrapp.Obj.ReadClass;
import com.ian.usrapp.R;
import com.ian.usrapp.ReadingContentActivity;

import java.util.List;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ViewHolder> {
    List<ReadClass> data;
    Context context;
    public ReadAdapter(List<ReadClass> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reader_items,parent,false);
        ViewHolder vh =new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(holder.itemView.getContext(), ReadingContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",data.get(position));
                it.putExtra("content",bundle);
                context.startActivity(it);
            }
        });
//        holder.content.setText(HtmlCompat.fromHtml(data.get(position).getContent(),HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title,content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
//            content = itemView.findViewById(R.id.content);
        }
    }
}
