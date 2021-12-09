package com.zypcy.sharding.sublibrarytable.service.impl;

import com.zypcy.sharding.sublibrarytable.entity.Member;
import com.zypcy.sharding.sublibrarytable.repository.MemberMapper;
import com.zypcy.sharding.sublibrarytable.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int insert(Member record) {
        return memberMapper.insertSelective(record);
    }

    @Override
    public Member selectByPrimaryKey(Long memberId) {
        return memberMapper.selectByPrimaryKey(memberId);
    }

    @Override
    public int deleteByPrimaryKey(Long memberId) {
        return memberMapper.deleteByPrimaryKey(memberId);
    }
}
