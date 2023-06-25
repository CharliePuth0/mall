package com.mall.member.dao;

import com.mall.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员等级
 * 
 * @author sugerdaddy
 * @email KFCv50@gmail.com
 * @date 2023-06-09 20:33:21
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {

//    void saveBasic(@Param("memberLevel") MemberLevelEntity memberLevel);
}
