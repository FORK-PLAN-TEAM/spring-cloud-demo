package com.zypcy.sharding.sublibrarytable.service;

import com.zypcy.sharding.sublibrarytable.entity.Member;

public interface IMemberService {

    int insert(Member record);

    Member selectByPrimaryKey(Long memberId);

    int deleteByPrimaryKey(Long memberId);
}
