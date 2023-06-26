package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.ware.entity.PurchaseDetailEntity;
import com.mall.common.utils.PageUtils;

import java.util.Map;

/**
 *
 *
 * @author wangyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

