package com.zypcy.springcloud.consulservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student") //对应consul配置中心上 student 前缀开始的配置信息
public class StudentConfig {

    private String name;
    private int age;
    private String sex;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "StudentConfig{" + "name='" + name + '\'' + ", age=" + age + ", sex='" + sex + '\'' + '}';
    }

}
