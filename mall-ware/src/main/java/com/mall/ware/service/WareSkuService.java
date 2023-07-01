package com.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.to.SkuHasStockVo;
import com.mall.ware.entity.WareSkuEntity;

import com.mall.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author mallyanfeng
 * @email 2011912434@qq.com
 * @date 2021-12-27 14:06:22
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}

