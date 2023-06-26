package com.mall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.ware.entity.PurchaseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author wangyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
