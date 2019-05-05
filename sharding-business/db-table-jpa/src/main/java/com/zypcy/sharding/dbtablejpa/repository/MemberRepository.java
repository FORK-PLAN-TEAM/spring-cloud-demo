package com.zypcy.sharding.dbtablejpa.repository;

import com.zypcy.sharding.dbtablejpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member , Long> {


}
