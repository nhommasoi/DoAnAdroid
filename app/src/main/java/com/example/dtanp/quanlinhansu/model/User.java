package com.example.dtanp.quanlinhansu.model;

public class User {
    String id;
    String userName;
    String pass;

    public User() {
    }

    public User(String id, String userName, String pass) {

        this.id = id;
        this.userName = userName;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
