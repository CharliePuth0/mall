package com.mall.search.controller;


import com.mall.common.es.SkuEsModel;
import com.mall.common.utils.R;
import com.mall.search.service.ESsaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.mall.common.exception.ExceptionCodeEnum.PRODUCT_UP_EXCEPTION;

@RestController
@RequestMapping("/search/save")
public class ESsaveController {

    @Autowired
    private ESsaveService eSsaveService;


    @PostMapping("/product")
    public R save(@RequestBody List<SkuEsModel> skuEsModels){

        try {
            eSsaveService.saveProduct(skuEsModels);
        }catch (IOException ioException){
            return R.error(PRODUCT_UP_EXCEPTION.getCode(), PRODUCT_UP_EXCEPTION.getMsg());
        }

        return R.ok();

    }


}
