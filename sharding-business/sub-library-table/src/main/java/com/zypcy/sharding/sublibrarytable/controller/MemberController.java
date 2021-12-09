package com.zypcy.sharding.sublibrarytable.controller;

import com.zypcy.sharding.sublibrarytable.entity.Member;
import com.zypcy.sharding.sublibrarytable.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zypcy.sharding.sublibrarytable.util.IdWorker;

import java.util.Date;

@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @RequestMapping("/add")
    public Member add() {
        Member member = new Member();
        //member.setMemberId(IdWorker.getLongId());
        member.setMemberName("张三");
        member.setNickName("闪耀的瞬间");
        member.setAccountNo(member.getMemberId() + "");
        member.setPassword("123465");
        member.setAge(27);
        member.setBirthDate(new Date());
        member.setEblFlag("1");
        member.setDelFlag("0");
        member.setDescription("xxx");
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
        memberService.insert(member);
        return member;
    }

    @RequestMapping("/findById")
    public Member findById(Long memberId) {
        return memberService.selectByPrimaryKey(memberId);
    }

    @RequestMapping("/delete")
    public String delete(Long memberId) {
        memberService.deleteByPrimaryKey(memberId);
        return "success";
    }
}
