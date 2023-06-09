package com.mall.member.dao;

import com.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author sugerdaddy
 * @email KFCv50@gmail.com
 * @date 2023-06-09 20:33:21
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
