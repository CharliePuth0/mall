package com.mall.product.feign;


import com.mall.common.es.SkuEsModel;
import com.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mall-search")
public interface SearchFeignService {


    @PostMapping("/search/save/product")
    R save(@RequestBody List<SkuEsModel> skuEsModels) ;

}
