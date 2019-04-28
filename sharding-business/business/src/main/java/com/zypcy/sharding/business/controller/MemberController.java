package com.zypcy.sharding.business.controller;

import com.zypcy.sharding.business.entity.Member;
import com.zypcy.sharding.business.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired private IMemberService memberService;

    @RequestMapping("/add")
    public String add(){
        addMember();
        return "success";
    }

    @RequestMapping("/getMemberById")
    public Member getMemberById(String memberId){
        return memberService.selectByPrimaryKey(memberId);
    }

    @RequestMapping("/delete")
    public String delete(String memberId){
        memberService.deleteByPrimaryKey(memberId);
        return "success";
    }

    private void addMember(){
        Member member = new Member();
        member.setMemberId("201904280001");
        member.setMemberName("张三");
        member.setNickName("闪耀的瞬间");
        member.setAccountNo("zhangsan");
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
}
