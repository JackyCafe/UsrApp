package com.ian.usrapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ian.usrapp.CheckActivity;
import com.ian.usrapp.MainApp;
import com.ian.usrapp.Obj.Check;
import com.ian.usrapp.R;
import com.ian.usrapp.Util.UsrPost;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {
    List<Check> datas;
    Context context ;
    MainApp app;
    public CheckAdapter(Context context,List<Check> datas)
    {
        this.datas = datas;
        this.context = context;
        app = (MainApp) ((CheckActivity)context).getApplication();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_activity_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_activity_title= itemView.findViewById(R.id.tv_activity_title);

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
        holder.tv_activity_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("user", app.getUserId());
                    json.put("title",datas.get(position).getId());
                    json.put("point",datas.get(position).getPoint());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                UsrPost usrPost = new UsrPost(app.getMy_activity_url());
                                usrPost.setToken(app.getToken());
                                String response = usrPost.doPost(json);
                                Looper.prepare();
                                Looper.loop();
                                Toast.makeText(context,"檔案已上傳",Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                } catch (Exception e) {
                    for (StackTraceElement se:e.getStackTrace())
                        app.Log(se.toString());

                    e.printStackTrace();
                }

                Log.v("Jacky",""+datas.get(position).toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }




}
