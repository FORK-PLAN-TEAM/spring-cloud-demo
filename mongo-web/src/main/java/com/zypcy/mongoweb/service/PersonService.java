package com.zypcy.mongoweb.service;

import com.zypcy.mongoweb.entity.Person;
import com.zypcy.mongoweb.mongodb.Page;

import java.util.List;

/**
 * @Author zhuyu
 * @Time 2020-06-09 10:41
 * @Description 描述
 */
public interface PersonService {

    String add(Person person);

    long del(String id);

    long update(Person person);

    Person getById(String id);

    List<Person> list(Person person);

    List<Person> listPage(Person person, Page page);
}
