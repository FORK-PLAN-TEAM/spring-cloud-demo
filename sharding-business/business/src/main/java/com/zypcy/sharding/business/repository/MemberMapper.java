package com.zypcy.sharding.business.repository;

import com.zypcy.sharding.business.entity.Member;

public interface MemberMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}