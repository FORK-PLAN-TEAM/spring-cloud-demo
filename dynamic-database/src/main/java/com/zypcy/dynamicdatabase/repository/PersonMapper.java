package com.zypcy.dynamicdatabase.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zypcy.dynamicdatabase.entity.Person;

public interface PersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(Person record);

    //默认insert 到 master
    @DS("master")
    int insertSelective(Person record);

    @DS("slave")
    Person selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}