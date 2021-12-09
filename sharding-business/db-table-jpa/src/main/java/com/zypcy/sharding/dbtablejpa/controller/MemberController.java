package com.zypcy.sharding.dbtablejpa.controller;

import com.zypcy.sharding.dbtablejpa.entity.Member;
import com.zypcy.sharding.dbtablejpa.repository.MemberRepository;
import com.zypcy.sharding.dbtablejpa.util.IdWorker;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/member")
@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping("/health")
    public String health() {
        return "success";
    }

    @RequestMapping("/findAll")
    public Object findAll() {
        return memberRepository.findAll();
    }

    @RequestMapping("/add")
    public Object add() {
        Member member = addMember();
        return memberRepository.save(member);
    }

    @RequestMapping("/findById")
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    private Member addMember() {
        Member member = new Member();
        member.setMemberId(IdWorker.getLongId());
        //SnowflakeShardingKeyGenerator keyGenerator = new SnowflakeShardingKeyGenerator();
        //member.setMemberId(Long.parseLong(keyGenerator.generateKey().toString()));
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
        return member;
    }

}
