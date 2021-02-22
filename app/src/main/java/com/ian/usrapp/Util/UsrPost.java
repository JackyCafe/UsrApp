package com.ian.usrapp.Util;

import com.google.gson.Gson;
import com.ian.usrapp.Util.InterNet;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UsrPost implements InterNet.IPost {
    URL url;
    HttpURLConnection conn;
    JSONObject  json;
    Gson gson = new Gson();
    String token;

    public UsrPost(String url) throws IOException {
        this.url = new URL(url);
        this.conn = (HttpURLConnection) this.url.openConnection();
        this.conn.setReadTimeout(3000);
        this.conn.setDoInput(true);
        this.conn.setDoOutput(true);
        this.conn.setRequestMethod("POST");
        this.conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//設定訊息的型別
        this.conn.setRequestProperty("Accept-Charset", "UTF-8");
    }

    public void setToken(String token) {
        this.token = token;
        this.conn.setRequestProperty("Authorization","Bearer " + token);

    }


    @Override
    public String doPost( JSONObject json) throws IOException {
        OutputStream out = conn.getOutputStream();//輸出流，用來發送請求，http請求實際上直到這個函式裡面才正式傳送出去
        out.write(json.toString().getBytes());//把json字串寫入緩衝區中
        out.flush();//重新整理緩衝區，把資料傳送出去，這步很重要
        out.close();
        int responseCode = conn.getResponseCode();
        InputStream in =  conn.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);
        return br.readLine();
    }



}
