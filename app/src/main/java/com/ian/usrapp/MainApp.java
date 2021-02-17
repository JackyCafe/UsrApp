package com.ian.usrapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

public class MainApp extends Application {
    SharedPreferences spf;
    SharedPreferences.Editor editor ;
    String token_url = "https://usr-test-304018.df.r.appspot.com/api/token/";
    String activity_url = "https://usr-test-304018.df.r.appspot.com/api/Activity/";
    String my_activity_url = "https://usr-test-304018.df.r.appspot.com/api/myActivity/";
    String reading_url = "https://usr-test-304018.df.r.appspot.com/api/reading/";





    @Override
    public void onCreate() {
        super.onCreate();
        spf  = getSharedPreferences("my_data.log",MODE_PRIVATE);
        editor = spf.edit();
    }


    public String getUserName(){
        return spf.getString("user","");
    }

    public void setUser(String user){
        editor.putString("user",user);
        editor.commit();
    }

    public String getPwdName()
    {
        return spf.getString("pwd","");
    }
    public void setPwd(String pwd){
        editor.putString("pwd",pwd);
        editor.commit();
    }

    public void setToken(String token)
    {
        editor.putString("token",token);
        editor.commit();
    }

    public String getToken()
    {
        return spf.getString("token","");
    }

    public void setUserId(int user_id){
        editor.putInt("user_id",user_id);
        editor.commit();
    }

    public int getUserId(){
        return spf.getInt("user_id", 0);
    }

    public void setScore(int score){
        editor.putInt("score",score);
        editor.commit();
    }

    public int getScore(){
        return spf.getInt("score", 0);
    }


    public String getToken_url() {
        return token_url;
    }

    public void setToken_url(String token_url) {
        this.token_url = token_url;
    }

    public String getActivity_url() {
        return activity_url;
    }

    public void setActivity_url(String activity_url) {
        this.activity_url = activity_url;
    }

    public String getMy_activity_url() {
        return my_activity_url;
    }

    public void setMy_activity_url(String my_activity_url) {
        this.my_activity_url = my_activity_url;
    }

    public String getReading_url() {
        return reading_url;
    }

    public void setReading_url(String reading_url) {
        this.reading_url = reading_url;
    }

    public void Log(String msg){
        Log.v("Jacky",msg);
    }
}
