package com.zypcy.sharding.business.service;

import com.zypcy.sharding.business.entity.Member;

public interface IMemberService {

    int insert(Member record);

    Member selectByPrimaryKey(String memberId);

    int deleteByPrimaryKey(String memberId);
}
