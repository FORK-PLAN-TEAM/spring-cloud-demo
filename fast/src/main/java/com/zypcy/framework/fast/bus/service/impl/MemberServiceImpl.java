package com.zypcy.framework.fast.bus.service.impl;

import com.zypcy.framework.fast.bus.entity.Member;
import com.zypcy.framework.fast.bus.mapper.MemberMapper;
import com.zypcy.framework.fast.bus.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-13
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
