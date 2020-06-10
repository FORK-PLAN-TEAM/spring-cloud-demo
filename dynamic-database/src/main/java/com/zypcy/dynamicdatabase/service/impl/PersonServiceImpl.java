package com.zypcy.dynamicdatabase.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zypcy.dynamicdatabase.entity.Person;
import com.zypcy.dynamicdatabase.repository.PersonMapper;
import com.zypcy.dynamicdatabase.service.svc.PersonService;
import com.zypcy.dynamicdatabase.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author zhuyu
 * @Time 2020-06-10 11:31
 * @Description 描述
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonMapper personMapper;

    @Override
    public int add(Person person) {
        person.setId(IdWorker.getId());
        person.setCreateTime(new Date());
        return personMapper.insert(person);
    }

    @Override
    public Person getById(String id) {
        return personMapper.selectByPrimaryKey(id);
    }
}
