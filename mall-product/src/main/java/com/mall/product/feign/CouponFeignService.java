package com.mall.product.feign;


import com.mall.common.to.SkuFullReductionTo;
import com.mall.common.to.SpuBoundTo;
import com.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-coupon")
public interface CouponFeignService {



    @PostMapping("coupon/spubounds/save")
    R save(@RequestBody SpuBoundTo spuBoundTo);


    @PostMapping("coupon/skufullreduction/save")
    R save(@RequestBody SkuFullReductionTo skuFullReductionTo);



}
