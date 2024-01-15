package com.minis.test;

import com.minis.web.RequestMapping;


public class HelloWorldBean {

    public String doGet() {
        System.out.println("\"hello world!\"");
        return "hello world!";
    }

    public String doPost() {
        return "hello world!";
    }

}
