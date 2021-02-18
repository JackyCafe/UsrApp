package com.ian.usrapp.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.ian.usrapp.Obj.Check;
import com.ian.usrapp.Util.InterNet;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UsrGet<T> implements InterNet.IGet {
    URL url;
    HttpURLConnection conn;
    JSONObject  json;
    Gson gson = new Gson();
    String token;
    public UsrGet(String url) throws IOException {
        this.url = new URL(url);
        this.conn = (HttpURLConnection) this.url.openConnection();
        this.conn.setReadTimeout(3000);
        this.conn.setDoInput(true);
        this.conn.setRequestMethod("GET");
        this.conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//設定訊息的型別
        this.conn.setRequestProperty("Accept-Charset", "UTF-8");

    }

    public void setToken(String token) {
        this.token = token;
        this.conn.setRequestProperty("Authorization","Bearer " + token);

    }

    @Override
    public String doGet()  {
        StringBuffer lines = null;
        try {
            conn.connect();
            InputStream in = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            lines = new StringBuffer();
            String line;
            while((line=br.readLine())!=null){
                lines.append(line);
            }
        }catch (Exception e){
             for (StackTraceElement se:e.getStackTrace())
                 Log.v("Jacky",se.toString());
        }
        return lines.toString();

    }

    @Override
    public Check parseJson(String response, Type type) {
        return null;
    }

    @Override
    public List<T> parseJsonList(String response, Type type) {
        List<T> objs = gson.fromJson(response, (Type) type);
        return objs;    }
}
