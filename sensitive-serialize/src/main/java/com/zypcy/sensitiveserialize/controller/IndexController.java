package com.zypcy.sensitiveserialize.controller;

import com.zypcy.sensitiveserialize.pojo.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 测试 通过注解 对返回的数据统一脱敏处理
 *
 * @Author zhuyu
 * @Time 2020-06-19 10:45
 * @Description 描述
 */
@RestController
public class IndexController {

    /**
     * 测试返回的数据中，身份证与手机号是否能自动脱敏
     *
     * @return
     */
    @GetMapping("/index")
    public ArrayList<Person> index() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("1", "zhuyu", "4307031991106075X", "15673163315", LocalDate.now()));
        persons.add(new Person("2", "zhuyu", "43070319911060751", "15573163316", LocalDate.now().plusDays(1)));
        persons.add(new Person("3", "zhuyu", "43070319911060752", "15573163311", LocalDate.now().plusDays(2)));
        persons.add(new Person("4", "zhuyu", "43070319911060753", "13873163323", LocalDate.now().plusDays(3)));
        return persons;
    }

}
