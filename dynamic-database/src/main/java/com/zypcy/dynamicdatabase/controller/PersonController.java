package com.zypcy.dynamicdatabase.controller;

import com.zypcy.dynamicdatabase.entity.Person;
import com.zypcy.dynamicdatabase.service.svc.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 64590 作者
 * @Time 2020-06-10 11:35
 * @Description 描述
 */
@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/add")
    public int add(@RequestBody Person person){
        return personService.add(person);
    }

    @GetMapping("/getById/{id}")
    public Person getById(@PathVariable String id){
        return personService.getById(id);
    }
}
