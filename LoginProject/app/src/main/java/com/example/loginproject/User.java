package com.example.loginproject;

public class User {
    private final String email;
    private final String name;
    private final String password;
    private final String number;

    public User(String email, String name, String password, String number) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getNumber() {
        return number;
    }
}
