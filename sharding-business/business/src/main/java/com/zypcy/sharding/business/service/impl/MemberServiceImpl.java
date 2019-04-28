package com.zypcy.sharding.business.service.impl;

import com.zypcy.sharding.business.entity.Member;
import com.zypcy.sharding.business.repository.MemberMapper;
import com.zypcy.sharding.business.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired private MemberMapper memberMapper;

    @Override
    public int insert(Member record) {
        return memberMapper.insert(record);
    }

    @Override
    public Member selectByPrimaryKey(String memberId) {
        return memberMapper.selectByPrimaryKey(memberId);
    }

    @Override
    public int deleteByPrimaryKey(String memberId) {
        return memberMapper.deleteByPrimaryKey(memberId);
    }
}
