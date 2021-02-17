package com.ian.usrapp.Util;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.lang.reflect.Type;

public interface InterNet {

    interface IPost<T> {
        public String doPost(JSONObject json) throws IOException;

        public T parseJson(String response, Type type);

        public List<T> parseJsonList(String response, Type type);
    }

    interface IGet<T> {
        public String doGet() throws IOException;

        public T parseJson(String response, Type type);

        public List<T> parseJsonList(String response, Type type);
    }
}