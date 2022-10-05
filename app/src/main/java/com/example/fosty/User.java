package com.example.fosty;

public class User {

    public String fullName, username, email, password;

    public User(){

    }

    public User(String fullName, String username, String email, String password){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
