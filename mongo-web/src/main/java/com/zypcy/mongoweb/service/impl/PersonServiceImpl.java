package com.zypcy.mongoweb.service.impl;

import com.zypcy.mongoweb.entity.Person;
import com.zypcy.mongoweb.mongodb.MongoDaoSupport;
import com.zypcy.mongoweb.mongodb.Page;
import com.zypcy.mongoweb.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author zhuyu
 * @Time 2020-06-09 10:42
 * @Description 描述
 */
@Service
public class PersonServiceImpl extends MongoDaoSupport<Person> implements PersonService {

    @Override
    public String add(Person person) {
        if (StringUtils.isEmpty(person.getName())) {
            return "fail";
        }
        super.save(person);
        return person.getId();
    }

    @Override
    public long del(String id) {
        Person person = new Person();
        person.setId(id);
        return super.deleteById(person).getDeletedCount();
    }

    @Override
    public long update(Person person) {
        return super.updateById(person.getId(), person).getModifiedCount();
    }

    @Override
    public Person getById(String id) {
        Person person = super.getById(id);
        return person;
    }

    @Override
    public List<Person> list(Person person) {
        return super.list(person);
    }

    @Override
    public List<Person> listPage(Person person, Page page) {
        return super.listPage(person, page);
    }
}
