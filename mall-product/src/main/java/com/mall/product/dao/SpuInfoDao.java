package com.mall.product.dao;

import com.mall.common.constant.ProductConstant;
import com.mall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;

/**
 * spu信息
 * 
 * @author sugerdaddy
 * @email KFCv50@gmail.com
 * @date 2023-06-09 20:01:12
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updaSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
