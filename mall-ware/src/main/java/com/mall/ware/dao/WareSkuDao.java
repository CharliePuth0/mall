package com.mall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.ware.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 * 
 * @author wangyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long getSkuStock(@Param("item") Long item);

}
