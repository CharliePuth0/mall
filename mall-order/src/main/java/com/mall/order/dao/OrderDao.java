package com.mall.order.dao;

import com.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author sugerdaddy
 * @email KFCv50@gmail.com
 * @date 2023-06-09 20:31:50
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
