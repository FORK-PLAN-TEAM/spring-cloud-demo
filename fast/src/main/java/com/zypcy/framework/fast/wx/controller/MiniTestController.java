package com.zypcy.framework.fast.wx.controller;

import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.wx.dto.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 微信小程序测试接口
 *
 * @Author zhuyu
 * @Date 2020-03-10
 */
@RequestMapping("/wx/test")
@RestController
public class MiniTestController {

    private static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person(IdWorker.getId(), "张三", 20, "湖南省长沙市天心区"));
        persons.add(new Person(IdWorker.getId(), "李四", 21, "湖南省长沙市岳麓区"));
        persons.add(new Person(IdWorker.getId(), "王五", 22, "湖南省长沙市开发区"));
        persons.add(new Person(IdWorker.getId(), "赵六", 23, "湖南省长沙市雨花区"));
        persons.add(new Person(IdWorker.getId(), "小黑", 24, "湖南省长沙市开福区"));
        persons.add(new Person(IdWorker.getId(), "老赵", 25, "湖南省长沙市市政府"));
        persons.add(new Person(IdWorker.getId(), "王二", 26, "湖南省长沙市梅溪湖"));
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Person getById(String id) {
        Optional<Person> person = persons.stream().filter((item) -> id.equals(item.getId())).findFirst();
        if (person.isPresent()) {
            return person.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Person> list() {
        return persons;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Person person) {
        if (person != null) {
            person.setId(IdWorker.getId());
            persons.add(person);
            return "success";
        }
        return "fail";
    }

}
