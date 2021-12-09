package com.zypcy.sharding.business;

import com.alibaba.fastjson.JSON;
import com.zypcy.sharding.business.entity.Member;
import com.zypcy.sharding.business.service.IMemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusinessApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private IMemberService memberService;
    private String memberId = "201904280001";

    @Test
    public void addMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberName("张三");
        member.setNickName("闪耀的瞬间");
        member.setAccountNo("zhuyu");
        member.setPassword("123465");
        member.setAge(27);
        member.setBirthDate(new Date());
        member.setEblFlag("1");
        member.setDelFlag("0");
        member.setDescription("屌丝一个");
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
        memberService.insert(member);
    }

    @Test
    public void selectMember() {
        Member member = memberService.selectByPrimaryKey(memberId);
        System.out.println("member:" + JSON.toJSONString(member));
    }
}
