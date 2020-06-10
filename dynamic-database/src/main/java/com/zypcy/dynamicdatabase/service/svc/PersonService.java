package com.zypcy.dynamicdatabase.service.svc;

import com.zypcy.dynamicdatabase.entity.Person;

/**
 * @Author zhuyu
 * @Time 2020-06-10 11:30
 * @Description 描述
 */
public interface PersonService {

    int add(Person person);

    Person getById(String id);

}
