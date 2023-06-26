package com.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.ware.dao.PurchaseDetailDao;
import com.mall.ware.entity.PurchaseDetailEntity;
import com.mall.ware.service.PurchaseDetailService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            //purchase_id  sku_id
            wrapper.and(w->{
                w.eq("purchase_id",key).or().eq("sku_id",key);
            });
        }


        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            //purchase_id  sku_id
            wrapper.eq("status",status);
        }

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
            //purchase_id  sku_id
            wrapper.eq("ware_id",wareId);
        }


        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper

        );

        return new PageUtils(page);
    }

}