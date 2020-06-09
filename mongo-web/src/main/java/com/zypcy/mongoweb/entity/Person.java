package com.zypcy.mongoweb.entity;

import com.zypcy.mongoweb.mongodb.MongoQueryField;
import com.zypcy.mongoweb.mongodb.MongoQueryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author zhuyu
 * @Time 2020-06-09 10:23
 * @Description 实体
 */
@Document(collection = "person")
public class Person implements Serializable {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 姓名
     */
    @Field("name")
    @MongoQueryField(type = MongoQueryType.LIKE)
    private String name;

    /**
     * 年龄
     */
    @Field("age")
    @MongoQueryField(type = MongoQueryType.EQUALS)
    private int age;

    /**
     * 创建时间
     */
    @Field("createTime")
    @MongoQueryField(type = MongoQueryType.GREATER_THAN, sort = "desc")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
