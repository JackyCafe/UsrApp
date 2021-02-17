package com.ian.usrapp.Obj;

import java.io.Serializable;

public class Token implements Serializable {
    public String refresh;
    public String access;

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Token(String refresh, String access) {
        this.refresh = refresh;
        this.access = access;
    }

    @Override
    public String toString() {
        return "Token{" +
                "refresh='" + refresh + '\'' +
                ", access='" + access + '\'' +
                '}';
    }
}
