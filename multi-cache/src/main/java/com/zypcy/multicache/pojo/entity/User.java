package com.zypcy.multicache.pojo.entity;

import java.io.Serializable;

/**
 * @Author 64590 作者
 * @Time 2020-06-24 10:30
 * @Description 描述
 */
public class User implements Serializable {

    private long userId;
    private String name;
    private int age;
    private String address;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
