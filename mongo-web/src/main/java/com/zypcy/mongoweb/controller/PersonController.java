package com.zypcy.mongoweb.controller;

import com.zypcy.mongoweb.entity.Person;
import com.zypcy.mongoweb.mongodb.Page;
import com.zypcy.mongoweb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.Date;
import java.util.List;

/**
 * @Author zhuyu
 * @Time 2020-06-09 10:57
 * @Description 描述
 */
@RestController()
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/add")
    public String add(@RequestBody Person person){
        person.setCreateTime(new Date());
        return personService.add(person);
    }

    @PostMapping("/update")
    public long update(@RequestBody Person person){
        return personService.update(person);
    }

    @PostMapping("/del")
    public long del(String id){
        return personService.del(id);
    }

    @GetMapping("/getById")
    public Person getById(String id){
        return personService.getById(id);
    }

    @PostMapping("/list")
    public List<Person> list(@RequestBody Person person){
        return personService.list(person);
    }

    @PostMapping("/listPage")
    public List<Person> listPage(@RequestBody Person person , @RequestBody Page page){
        return personService.listPage(person , page);
    }
}
