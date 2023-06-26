package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.ware.entity.WareOrderTaskEntity;
import com.mall.common.utils.PageUtils;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author mallyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}

