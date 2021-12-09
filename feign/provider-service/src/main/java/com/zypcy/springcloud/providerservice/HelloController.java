package com.zypcy.springcloud.providerservice;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(String name) {
//        try {
//            Thread.sleep(60 * 1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return "hello " + name;
    }


    @RequestMapping("/createPerson")
    public Person create(@RequestBody Person person) {
        Person p = new Person();
        p.setAge(person.getAge());
        p.setName(person.getName());
        p.setId(RandomUtils.nextInt(1000));
        System.out.println("provider Person： " + p.toString());
        return p;
    }

}
