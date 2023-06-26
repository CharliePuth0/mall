package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.ware.entity.PurchaseEntity;
import com.mall.common.utils.PageUtils;

import java.util.Map;

/**
 * 采购信息
 *
 * @author mallyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

}

